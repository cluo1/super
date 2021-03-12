package com.luo.user.service;


import javax.servlet.http.HttpSession;

/**
 * @ClassName : com.iflytek.vie.app.api.user
 * @Description :
 * @Version : 1.0
 * @Author : lpniu
 * @Date: 2021/3/11 19:47
 */

public interface UserService {
    String login(String loginName, String passWord, HttpSession session) throws Exception;

    Object getAppInfo() throws Exception;

    Object getAuthorities(String xfTicket, HttpSession session) throws Exception;

    Object getRoleByAppCodeUserId(String xfTicket, HttpSession session) throws Exception;

    Object getUserByOrgId(String xfTicket, HttpSession session) throws Exception;

    Object getUserInfo(String xfTicket, HttpSession session)throws Exception;
}