package com.jiuxin.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常訪問ok的方法
     * @param id
     * @return
     */
   public String paymentInfo_ok(Integer id){

       return "线程池"+Thread.currentThread().getName()+"paymentInfo_ok"+id +"\t"+"O(∩_∩)O哈哈~" ;
   }

    /**
     * 服务降级注解 @HystrixCommand
     * @HystrixCommand：此注解表示此方法是hystrix方法，其中fallbackMethod定义回退方法的名称
     * @param id
     * @return
     */

   @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
           @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
   })
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
           // TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()+"paymentInfo_TimeOut"+id +"\t"+"O(∩_∩)O哈哈~" ;
    }


    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池"+Thread.currentThread().getName()+"Handler系统繁忙"+id +"\t"+"hehe~" ;
    }

    //----------------------服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),
            //是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
            // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            //时间窗口前时间范围
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")
           //失败率达到多少
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
       if(id<0){
          throw new RuntimeException("********id不能为负数");
       }
       String serialNumber =IdUtil.simpleUUID();
       return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;

   }
   public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
   {
       return "id不能为负数，请稍后再试 id："+id;
   }
}
