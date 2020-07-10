package com.jiuxin.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
 */
@Configuration
public class ApplicationContextConfig {
    /**
     *  @LoadBalanced注解赋予RestTemplate负载均衡的能力
     * @return
     */
    @Bean
   // @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
