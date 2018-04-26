package com.example.ribbonconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 三、服务发现与消费(Eureka客户端)
 * @EnableDiscoveryClient 注解让该应用注册为eureka客户端应用，以获得服务发现能力
 * @EnableCircuitBreaker 注解开启断路器
 *
 * 可以使用@SpringCloudApplication注解替代以下三个注解
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class RibbonConsumerApplication {

	/**
	 * 创建RestTemplate实例 并开启客户端负载均衡
	 * Ribbon 通过轮询的方式实现负载均衡
	 * @return
	 */
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication.class, args);
	}
}
