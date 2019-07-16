package com.luo.feignClient.service.impl;

import com.luo.feignClient.service.DemoService;


public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(String type) throws Exception {
        throw new Exception("调用失败");
    }
}
