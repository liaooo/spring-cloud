package com.example.feignconsumer;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @EnableFeiginClients 注解开启Spring Cloud Feign功能
 */
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignConsumerApplication {

	/**
	 * Feign客户端默认的Logger.Level为NONE级别，该级别不会记录任何Feign调用的日志
	 * 创建实例覆盖默认级别，这里的配置时全局配置。
	 * 也可以在配置类中创建，定义局部的级别
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		/*
		NONE：不记录任何信息
		BASIC：仅记录请求方法、URL及响应状态码和执行时间
		HEADERS：在BASIC基础上记录请求和相应的头信息
		FULL：记录所有请求与相应的明细，包括头信息，请求体，元数据等
		 */
		return Logger.Level.FULL;
	}

	public static void main(String[] args) {
		SpringApplication.run(FeignConsumerApplication.class, args);
	}
}
