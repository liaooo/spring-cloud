package com.example.helloserviceapi.service;

import com.example.helloserviceapi.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 * 抽离出公共的API接口，供服务提供方和消费方实现
 */
@RequestMapping("/refactor")
public interface HelloService {
    @RequestMapping(value = "/hello4", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello5", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello6", method = RequestMethod.POST)
    String hello(@RequestBody User user);
}
