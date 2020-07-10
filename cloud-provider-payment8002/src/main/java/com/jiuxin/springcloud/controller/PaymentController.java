package com.jiuxin.springcloud.controller;
import com.jiuxin.springcloud.service.PaymentService;
import com.juxin.springcloud.entities.CommentResult;
import com.juxin.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value="/payment/create")
    public CommentResult creat(@RequestBody Payment payment){
        int result =paymentService.creat(payment);
        log.info("charu jieguo--------------"+result);
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
        log.info("charu jieguo--------------"+result);
        if(result != null){
            return new CommentResult(200, "查询成功,serverPort:"+serverPort,result);
        }else{
            return new CommentResult(200, "没有对应的记录查询失败"+id,null);
        }
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
}
