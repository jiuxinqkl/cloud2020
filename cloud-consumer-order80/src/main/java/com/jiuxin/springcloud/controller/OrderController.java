package com.jiuxin.springcloud.controller;

import com.jiuxin.springcloud.lb.LoadBalancer;
import com.juxin.springcloud.entities.CommentResult;
import com.juxin.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * RestTemplate默认使用HttpMessageConverter实例将HTTP消息转换成POJO或者从POJO转换成HTTP消息。默认情况下会注册主mime类型的转换器，但也可以通过setMessageConverters注册其他的转换器。
 * RestTemplate是Spring提供的用于访问Rest服务的客户端，RestTemplate提供了多种便捷访问远程Http服务的方法,能够大大提高客户端的编写效率。
 */
@RestController
@Slf4j
public class OrderController {

    //public static final String PATMENT_URL="http://localhost:8001"; 单台
    //集群
    public static final String PATMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalancer loadBalancer;
    @GetMapping("/consumer/payment/create")
    public CommentResult<Payment> creat(Payment payment){
        return restTemplate.postForObject(PATMENT_URL+"/payment/create",payment,CommentResult.class);

    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PATMENT_URL+"/payment/get/"+id,CommentResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommentResult getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommentResult> entity=restTemplate.getForEntity(PATMENT_URL+"/payment/get/"+id,CommentResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommentResult<>(444,"操作失败");
        }
    }

    @GetMapping(value="/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances ==null || instances.size() <=0){
            return null;
        }
        ServiceInstance serviceInstance =loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result =restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
        return result;
    }
}
