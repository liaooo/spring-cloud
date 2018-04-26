package com.example.helloservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注入DiscoveryClient
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/hello")
    public String hello() throws InterruptedException {
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        logger.info("/hello, host=" + instance.getHost() + ", serviceId=" + instance.getServiceId());
        int delay = new Random().nextInt(3000);
        logger.info("sleep " + delay + "ms");
        Thread.sleep(delay);
        return "Hello";
    }

}
