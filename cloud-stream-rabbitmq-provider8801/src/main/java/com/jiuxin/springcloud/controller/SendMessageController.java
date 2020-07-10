package com.jiuxin.springcloud.controller;

import com.jiuxin.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;

    @GetMapping(value ="/sendMessage")
    public String SendMessage(){
        return messageProvider.send();
    }
}
