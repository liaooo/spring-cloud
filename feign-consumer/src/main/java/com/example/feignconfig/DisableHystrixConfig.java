package com.example.feignconfig;

import feign.Feign;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 自定义客户端配置
 * Feign全局参数feign.hystrix.enable=true/false
 * 或者hystrix.command.default.execution.timeout.enabled=true/false
 *
 * @Configuration 注解的类不能与@ComponentScan注解扫描的包重叠，如果包重叠，将会导致所有的FeignClient都会使用该配置。
 */
@Configuration
public class DisableHystrixConfig {

    /**
     * 针对某个服务客户端关闭hystrix支持，指定客户端配置Feign.Builder实例
     * @return
     */
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS;
    }
}
