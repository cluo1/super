package com.luo.feignClient.service;

import com.luo.feignClient.entity.User;
import com.luo.feignClient.service.impl.DemoServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "demo",fallback = DemoServiceImpl.class)
public interface DemoService {

    @GetMapping("/demo/feign")
    public String hello(User user);
}
