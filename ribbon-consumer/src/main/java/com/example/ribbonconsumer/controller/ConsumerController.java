package com.example.ribbonconsumer.controller;

import com.example.ribbonconsumer.service.HelloService;
import com.example.ribbonconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @Autowired
    UserService userService;

    @RequestMapping("/ribbon-consumer")
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping("/users/{id}")
    public String user(@PathVariable("id") Long id) {
        return userService.users(id);
    }
}
