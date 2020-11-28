package com.boustead.SeleniumAutoScaler;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.api.model.autoscaling.v1.Scale;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
//import io.kubernetes.client.openapi.ApiClient;
//import io.kubernetes.client.openapi.ApiException;
//import io.kubernetes.client.openapi.Configuration;
//import io.kubernetes.client.openapi.apis.CoreV1Api;
//import io.kubernetes.client.openapi.models.*;
//import io.kubernetes.client.util.Config;

import io.kubernetes.client.proto.V1Apps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScaleService {

    Logger log = LogManager.getLogger();

    @Autowired
    ConfigProperties configProperties;

    /*
    scaleDeployments
    Uses fabric8 library to locate and communicate with API Server
    Scaled deployment to passed integer
     */
    //ToDo: Add checks
    public void scaleDeployment(int scaleValue){
        log.info("Scaling deployment...");
        Config config = new ConfigBuilder().build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        client.apps().deployments().inNamespace(configProperties.getNamespace()).withName(configProperties.getDeployment()).scale(scaleValue, true);
    }



}
