package com.jiuxin.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderNacosController {
    public static final String SERVER_URL= "http://nacos-";

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String getServerUrl;


    @GetMapping(value="/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id){
       return restTemplate.getForObject(SERVER_URL +"/payment/nacos/"+id,String.class);
    }
}
