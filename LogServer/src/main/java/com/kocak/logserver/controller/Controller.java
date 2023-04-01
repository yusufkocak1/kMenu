package com.kocak.logserver.controller;

import com.kocak.logserver.dto.LogBody;
import com.kocak.logserver.dto.LogMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/log")
public class Controller {

    @PostMapping("/add")
    public String addLog(LogMessage logRequest){
        LogBody.builder("").build();
        System.out.printf(logRequest.toString());
        return "saved!";
    }
@RequestMapping(path = "/test")
public String test(){
        return "succes";
    }
}
