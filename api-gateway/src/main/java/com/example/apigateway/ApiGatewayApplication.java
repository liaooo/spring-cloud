package com.example.apigateway;

import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * @EnableZuulProxy 注解开启Zuul的api网管服务功能
 * 它包含了对Hystrix和Ribbon的支持，可设置参数调整配置
 */
@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {

		//FilterProcessor.setProcessor(FilterProcessor.getInstance());

		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/**
	 * 注册过滤器Bean使其生效
	 * @return
	 */
	//@Bean
	/*public AccessFilter accessFilter() {
		return new AccessFilter();
	}*/

	/**
	 * 自定义路由规则，默认情况下，采用服务名作为前缀
	 * @return
	 */
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
		// 匹配以name-version的格式命名的服务，路由规则为/version/name，否则以默认的路由规则
		return new PatternServiceRouteMapper(
				"(?<name>^.+)-(?<version>v.+$)",
				"${version}/${name}");
	}
}
