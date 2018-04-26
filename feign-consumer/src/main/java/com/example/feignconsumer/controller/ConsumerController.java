package com.example.feignconsumer.controller;

import com.example.feignconsumer.bean.User;
import com.example.feignconsumer.service.HelloService;
import com.example.feignconsumer.service.RefactorHelloService;
import com.example.feignconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    /**
     * 可直接对绑定了@FeignClient的接口调用
     */
    @Autowired
    private HelloService helloService;

    @Autowired
    private UserService userService;

    @Autowired
    private RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String hello2() {
        StringBuilder sb = new StringBuilder();
        sb.append(userService.hello("admin")).append("\n")
                .append(userService.hello("test", 23)).append("\n")
                .append(userService.hello(new User("user", 34)));
        return sb.toString();
    }

    @RequestMapping(value = "/feign-consumer3", method = RequestMethod.GET)
    public String hello3() {
        StringBuilder sb = new StringBuilder();
        sb.append(refactorHelloService.hello("admin")).append("\n")
                .append(refactorHelloService.hello("test", 23)).append("\n")
                .append(refactorHelloService.hello(new com.example.helloserviceapi.dto.User("user", 34)));
        return sb.toString();
    }
}
