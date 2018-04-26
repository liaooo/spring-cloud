package com.example.helloservice.controller;

import com.example.helloservice.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RequestMapping("/users")
@RestController
public class UserContoller {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) throws Exception {
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        logger.info("/hello, host=" + instance.getHost() + ", serviceId=" + instance.getServiceId());
        int delay = new Random().nextInt(3000);
        Thread.sleep(delay);
        return new User(id, "test" + new Random().nextInt());
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @RequestMapping("/hello1")
    public User user(@RequestHeader String name, @RequestHeader Integer age) {
        User user = new User();
        user.setId(0L);
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.POST)
    public String user(@RequestBody User user) {
        return user.toString();
    }
}
