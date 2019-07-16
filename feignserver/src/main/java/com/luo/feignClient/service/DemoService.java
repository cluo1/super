package com.luo.feignClient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "demo")
public interface DemoService {

    @GetMapping("/demo/feign")
    public String hello(String type) throws Exception;
}
