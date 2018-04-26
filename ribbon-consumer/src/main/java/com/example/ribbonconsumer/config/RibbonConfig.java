package com.example.ribbonconsumer.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @RibbonClient 注解指定客户端配置类（更详细的配置）
 * Camden版本之后 可以通过<clientName>.ribbon.<key>=<value>配置
 * client为服务名，key为PropertiesFactory中定义的几个属性及CommonClientConfigKey中的属性
 * 不指定clientName则表示全局配置
 * 如：hello-service.ribbon.NFLoadBalancerPingClassName=com.netflix.loadbalancer.PingUrl
 */
//@Configuration
//@RibbonClient(name = "hello-service", configuration = HelloSerciceConfig.class)
public class RibbonConfig {

    /**
     * 可以实例化Bean覆盖默认配置
     * @return
     */
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }
}
