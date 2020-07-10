package com.jiuxin.springcloud.controller;
import com.jiuxin.springcloud.service.PaymentService;
import com.juxin.springcloud.entities.CommentResult;
import com.juxin.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;
    /**
     * 第一加入DiscoveryClient
     */
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value="/payment/create")
    public CommentResult creat(@RequestBody Payment payment){
        int result =paymentService.creat(payment);
        log.info("插入结果--------------"+result);
        if(result > 0){
            return new CommentResult(200, "插入数据成功,serverPort:"+serverPort,result);
        }else{
            return new CommentResult(200, "插入数据失败",null);
        }
    }

    /**
     * 通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中：URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx“) 绑定到操作方法的入参中。
     * @param id
     * @return
     */
    @GetMapping(value="/payment/get/{id}")
    public CommentResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment result =paymentService.getPaymentById(id);
        log.info("查出结果--------------"+result);
        if(result != null){
            return new CommentResult(200, "查询成功,serverPort:"+serverPort,result);
        }else{
            return new CommentResult(200, "没有对应的记录查询失败"+id,null);
        }
    }

    @GetMapping(value="/payment/discovery")
    public Object  discovery(){
        List<String> services = discoveryClient.getServices(); //实例
        for(String element : services){
            log.info("++++++element+++++++"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
    @GetMapping(value="/payment/lb")
    public String getPayment(){
        return serverPort;
    }

    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "hi,i`am oaymentzipkin server fall back,welcome to jiuxin.";
    }
}
/**
 *itar 生成array for代码块
 *
 * for (int i = 0; i < array.length; i++) {
 *              = array[i];
 *         }
 *         itco 生成Collection迭代 
 *
 *  for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
 *             Object next =  iterator.next();
 *         }
 * iten 生成enumeration遍历
 *
 * while (enumeration.hasMoreElements()) {
 *             Object nextElement =  enumeration.nextElement();
 *         }
 *iter 生成增强for循环
 *
 * for (String arg : args) {
 *         }
 *
 *         itli 生成List的遍历
 *
 * for (int i = 0; i < list.size(); i++) {
 *             Object o =  list.get(i);
 *         }
 **/