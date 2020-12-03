package com.boustead.SeleniumAutoScaler;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class HTTPRequestsTest {

    @Autowired
    HTTPRequests httpRequests;

    @Test
    public void testResponseFromHubAndGetQueue(){
        String jsonStr= "{  \"browserTimeout\": 600,  \"capabilityMatcher\": \"org.openqa.grid.internal.utils.DefaultCapabilityMatcher\",  \"cleanUpCycle\": 5000,  \"custom\": {  },  \"debug\": false,  \"host\": \"172.17.0.5\",  \"jettyMaxThreads\": -1,  \"newSessionRequestCount\": 5,  \"newSessionWaitTimeout\": -1,  \"port\": 4444,  \"registry\": \"org.openqa.grid.internal.DefaultGridRegistry\",  \"role\": \"hub\",  \"servlets\": [  ],  \"slotCounts\": {    \"free\": 0,    \"total\": 0  },  \"success\": true,  \"throwOnCapabilityNotPresent\": true,  \"timeout\": 600,  \"withoutServlets\": [  ]}";

        try{
            JSONObject json = new JSONObject(jsonStr);
            int queueSize = json.getInt("newSessionRequestCount");
            assertEquals(queueSize, 5);
        }catch(Exception e){
            fail();
        }

    }
}