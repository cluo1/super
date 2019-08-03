//package com.luo.service.impl;
//
//import com.luo.entity.User;
//import com.luo.mapper.UserMapper;
//import com.luo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userMapper.loadUserByUsername(s);
//        if(user == null){
//            throw new UsernameNotFoundException("用户不存在");
//        }
//
//        return user;
//    }
//}
