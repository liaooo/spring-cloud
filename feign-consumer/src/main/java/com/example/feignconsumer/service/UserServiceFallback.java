package com.example.feignconsumer.service;

import com.example.feignconsumer.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class UserServiceFallback implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceFallback.class);

    @Override
    public String hello(@RequestParam("name") String name) {
        logger.info("hello(String name).fallback");
        return "error{" + name + "}";
    }

    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        logger.info("hello(String name, Integer age).fallback");
        return new User("unknown", 0);
    }

    @Override
    public String hello(@RequestBody User user) {
        logger.info("hello(User user).fallback");
        return "error{" + user + "}";
    }
}
