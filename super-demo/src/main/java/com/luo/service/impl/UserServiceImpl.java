package com.luo.service.impl;

import com.luo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public UserServiceImpl(){
        System.out.println("This is UserServiceImpl");
    }

    @Override
    public void say() {
        System.out.println("This is UserServiceImpl1");

    }
}
@Service
class UserServiceImpl2 implements UserService{

    @Override
    public void say() {
        System.out.println("This is UserServiceImpl2");
    }
}