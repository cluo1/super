package com.luo.engin.controller;

import com.luo.engin.service.EngineProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 引擎服务
 */
@RestController
@RequestMapping("/engin")
public class EngineController {

    @Autowired
    private EngineProcess engineProcess;


    @RequestMapping("/enginVoiceAnalyize")
    public String enginVoiceAnalyize(){

        String result = engineProcess.enginVoiceAnalyize();
        return result;

    }

    public static void main(String[] args) {
//        engineProcess.enginVoiceAnalyize();

    }

}
