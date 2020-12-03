package com.boustead.SeleniumAutoScaler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HTTPRequests {

    Logger log = LogManager.getLogger(HTTPRequests.class);

    @Autowired
    ConfigProperties configProperties;

    /*
    Make HTTP request to booking servlet to find the number of
    users making booking requests
     */
    public int requestNumberOfUserBookings() {

        log.info("Request number of user bookings...");

        // Create URL
        // If there is an issue with the URI, return the default scale
        URI uri = createURI("http://" + configProperties.getUserRequestIp() + ":" + configProperties.getUserRequestPort() + "/userRequests");
        if(uri==null){ return configProperties.getDefaultScale(); }

        //Create request
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        // Send request
        // If the request fails for any reason, return the default scaling
        HttpResponse<String> response = sendHTTPRequest(request);
        if(response==null){ return configProperties.getDefaultScale(); }

        // Try convert String response to integer
        // Compare integer to max scale and return the lowest value, if failed return default
        int responseInt = getIntegerFromResponse(response.body());
        return responseInt;

    }

    /*
    Continuously checks hub queue and returns a few minutes after queue is empty
    Returns false if any error was enountered
     */
    public void checkHubQueueAndWait(){

        // counter to keep track of the number of loops
        // if counter exceeds max minutes then break
        int loopCounter = 0;
        int maxMinutesWait = 10;

        //Create URL
        URI uri = createURI("http://" + configProperties.getHubIp() + ":" + configProperties.getHubPort() + "/grid/api/hub/");
        if(uri==null){ return; }

        //Create request
        log.info("Sending request to get queue...");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        //Continuously loop until queue is empty
        boolean completeFlag = false;
        while(!completeFlag){

            // Send request
            // Return if anything failed
            HttpResponse<String> response = sendHTTPRequest(request);
            if(response==null){ return; }

            // Get count from body
            // Return true if queue is 0 or error
            completeFlag = checkResponseForQueueAndReturnCompletion(response.body());

            // Sleep
            // If queue is not empty, this will give some time before checking again
            // If queue is empty, this will give some time for nodes to complete requests
            Boolean sleepComplete = sleeping();
            if(sleepComplete==false){ return; }


            loopCounter++;
            if(loopCounter>maxMinutesWait){
                log.error("Queue is taking longer than limited minutes to respond");
                log.error("Returning without letting queue empty");
                return;
            }
        }

        // loop completed successfully
        return;
    }

    // Try create URL
    // Return null if error
    private URI createURI(String uriStr){
        log.info("Uri String: " + uriStr);
        try{
            return new URI(uriStr);
        }catch(URISyntaxException e){
            log.error("URI is invalid");
            log.error(e.getMessage());
            return null;
        }
    }

    //Send HTTP request and check status code
    // return null if failed or status not 200-299
    private HttpResponse<String> sendHTTPRequest(HttpRequest request){
        try {
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 300 && response.statusCode() > 199){
                log.info("Status code is: " + response.statusCode());
                return response;
            }else{
                log.error("Status code is: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("Request failed.");
            log.error(e.getMessage());
            return null;
        }
    }

    // Try convert String response to integer
    // Compare integer to max scale and return the lowest value
    // If anything fails, return default
    private Integer getIntegerFromResponse(String responseBody){
        try {
            int returned = Integer.parseInt(responseBody);
            if(returned>configProperties.getMaxScale()){ return configProperties.getMaxScale(); }
            else { return returned; }
        } catch (Exception e){
            log.error("Unable to convert response to integer");
            log.info("Setting to default request value");
            return configProperties.getDefaultScale();
        }
    }

    // Converts the response body string into json and parse to find newSessionRequestCount
    // Return true if queue is zero
    // Return true if there was an error so the parent method will return to stop waiting (Assume 0)
    // Return false if there are items in queue
    private Boolean checkResponseForQueueAndReturnCompletion(String responseBody){
        int queueSize;
        try{
            JSONObject json = new JSONObject(responseBody);
            queueSize = json.getInt("newSessionRequestCount");
        }catch(Exception e){
            log.error("Unable to get key/value from json");
            log.error(e.getMessage());
            //Assumes queue is empty and returns from method (stops waiting)
            queueSize = 0;
        }
        // Check if complete
        if(queueSize==0){
            return true;
        }else{
            return false;
        }

    }


    // Sleep method has two uses
    // If the queue is empty then it will give existing nodes to process their current requests before downscaling
    // If the queue is not empty then it will give some time for queue to go down before re-checking
    private Boolean sleeping(){
        try {
            Thread.sleep(60*1000);
            return true;
        } catch (InterruptedException e) {
            log.error("Error sleeping thread");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }
}
