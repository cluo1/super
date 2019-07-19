package com.luo.feignClient.service.impl;

import com.luo.feignClient.entity.User;
import com.luo.feignClient.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(User user){
        return "服务不可用";
    }
}
