package com.example.feignconsumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 继承公共API,注解FeignClient绑定服务
 */
@FeignClient(value = "hello-service")
public interface RefactorHelloService extends com.example.helloserviceapi.service.HelloService{
}
