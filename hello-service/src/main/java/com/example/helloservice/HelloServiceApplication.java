package com.example.helloservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 二、服务提供者(Eureka客户端)
 * @EnableDiscoveryClient 注解 激活eureka中的DiscoveryClient实现（自动化配置）
 *
 * DiscoveryClient.class：
 * 	该类与EurekaServer协作
 * 	向server注册服务实例
 * 	向server续租
 * 	服务关闭时向server取消租约（下线）
 * 	检索server服务列表（服务消费者）
 */
@EnableDiscoveryClient
@SpringBootApplication
public class HelloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloServiceApplication.class, args);
	}
}
