package com.example.feignconsumer.service;

import com.example.feignconsumer.bean.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 在定义参数绑定时
 * @RequestParam、@RequestHeader、@PathVariable 绑定参数的value不能少
 * 不能使用@GetMapping和@PostMapping这样的组合接口，只能使用@RequestMapping
 */
@FeignClient(name = "hello-service", fallback = UserServiceFallback.class)
public interface UserService {

    @RequestMapping(value = "/users/hello", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/users/hello1", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/users/hello2", method = RequestMethod.POST)
    String hello(@RequestBody User user);
}
