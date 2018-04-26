package com.example.ribbonconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello() {
        long callStart = System.currentTimeMillis();
        String url = "http://HELLO-SERVICE/hello";
        String result = restTemplate.getForEntity(url, String.class).getBody();
        long callEnd = System.currentTimeMillis();
        logger.info("调用" + url + "耗时" + (callEnd - callStart) + "ms");
        return result;
    }

    private String helloFallback() {
        return "error";
    }
}
