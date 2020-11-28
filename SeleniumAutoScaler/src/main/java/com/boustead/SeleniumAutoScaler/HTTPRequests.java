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
        String strUri = "http://" + configProperties.getUserRequestIp() + ":" + configProperties.getUserRequestPort() + "/userRequests";
        log.info("User request uri: " + strUri);
        URI uri;
        try{
            uri = new URI(strUri);
        }catch(URISyntaxException e){
            log.error("URI is invalid");
            log.error(e.getLocalizedMessage());
            return configProperties.getDefaultScale(); //return default
        }

        //Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        // Send request
        // If the request fails for any reason, return the default scaling
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("Request failed: Get user requests.");
            log.error(e.getLocalizedMessage());
            return configProperties.getDefaultScale();
        }

        // Check response code
        // If the response code is not OK, return the default scale
        if(response.statusCode() != 200){
            log.error("Request failed: Get user requests");
            log.error("Status code: " + response.statusCode());
            return configProperties.getDefaultScale();
        }

        // Try convert String response to integer
        // Compare integer to max scale and return the lowest value
        // If anything fails, return default
        try {
            int returned = Integer.parseInt(response.body());
            if(returned>configProperties.getMaxScale()){ return configProperties.getMaxScale(); }
            else { return returned; }
        } catch (Exception e){
            log.error("Unable to convert response to integer");
            log.info("Setting to default request value");
            return configProperties.getDefaultScale();
        }
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
        String strUri = "http://" + configProperties.getHubIp() + ":" + configProperties.getHubPort() + "/grid/api/hub/";
        log.info("Hub queue uri: " + strUri);
        URI uri;
        try{
            uri = new URI(strUri);
        }catch(URISyntaxException e){
            log.error("URI is invalid");
            log.error(e.getLocalizedMessage());
            return;
        }

        //Create request
        log.info("Sending request to get queue...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        //Continuously loop until queue is empty
        boolean completeFlag = false;
        while(!completeFlag){

            // Send request
            HttpResponse<String> response = null;
            try {
                response = HttpClient.newBuilder()
                        .build()
                        .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                log.error("Request failed: Get user requests.");
                log.error(e.getLocalizedMessage());
                return;
            }

            // Check response code
            if(response.statusCode() != 200){
                log.error("Request failed: Get user requests");
                log.error("Status code: " + response.statusCode());
                return;
            }

            // Get count from body
            JSONObject json = new JSONObject(response.body());
            int queue = json.getInt("newSessionRequestCount");

            // Check if complete
            if(queue==0){
                completeFlag=true;
            }

            // Sleep
            // If queue is not empty, this will give some time before checking again
            // If queue is empty, this will give some time for nodes to complete requests
            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                log.error("Error sleeping thread");
                log.error(e.getLocalizedMessage());
                return;
            }

            loopCounter++;
            if(loopCounter>maxMinutesWait){
                log.error("Queue is taking longer than 10 minutes to respond");
                log.error("Returning without letting queue empty");
                return;
            }
        }

        // loop completed successfully
        return;

    }
}
