package com.example.feignconsumer.service;

import com.example.feignconfig.DisableHystrixConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 定义接口，通过@FeignClient注解指定服务名并绑定服务
 * 然后使用SpringMVC的注解来绑定具体该服务提供的REST接口
 * 该注解会创建一个Feign客户端，同时也会创建一个Ribbon客户端，且客户端的方法都会封装到hystrix命令中进行服务保护
 * value参数绑定的服务名，configuration参数引入配置，fallback参数指定服务降级实现类
 * 会为每一个客户端创建一个feign.Logger实例
 */
@FeignClient(value = "hello-service",
        fallback = HelloServiceFallback.class,
        configuration = DisableHystrixConfig.class)
public interface HelloService {
    @RequestMapping("/hello")
    String hello();
}
