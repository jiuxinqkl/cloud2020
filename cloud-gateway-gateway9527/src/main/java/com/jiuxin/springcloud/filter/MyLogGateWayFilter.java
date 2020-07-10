package com.jiuxin.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @controller 控制器（注入服务）
 *
 * 用于标注控制层，相当于struts中的action层
 * 2、@service 服务（注入dao）
 *
 * 用于标注服务层，主要用来进行业务的逻辑处理
 * 3、@repository（实现dao访问）
 *
 * 用于标注数据访问层，也可以说用于标注数据访问组件，即DAO组件.
 * 4、@component （把普通pojo实例化到spring容器中，相当于配置文件中的
 * <bean id="" class=""/>）
 *
 * 泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
 * 案例：<context:component-scan base-package=”com.*”>
 */
@Component
@Slf4j
public class MyLogGateWayFilter  implements GlobalFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("______come in MyLogGateWayFilter___________"+ new Date());
      String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if(uname==null){
            log.info("*****用户名为null，非法用户*********************");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//设置http状态吗，不被接受
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange); //过滤链
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
