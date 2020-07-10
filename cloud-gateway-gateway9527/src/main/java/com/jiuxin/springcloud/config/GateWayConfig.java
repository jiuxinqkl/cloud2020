package com.jiuxin.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码配置转发
 */

@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder  routeLocatorBuilder){
       RouteLocatorBuilder.Builder routes= routeLocatorBuilder.routes();
       routes.route("path_routh_jiuxin",r->r.path("/guonei").uri("http://news/baidu.guonei")).build();
        return routes.build();
    }
}
