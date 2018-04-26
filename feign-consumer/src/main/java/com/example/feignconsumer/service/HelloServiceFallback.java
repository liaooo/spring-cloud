package com.example.feignconsumer.service;

import org.springframework.stereotype.Component;

/**
 * 实现客户端接口，实现服务降级处理
 */
@Component
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "error";
    }
}
