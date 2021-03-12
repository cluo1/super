package com.luo.user.control;


import com.luo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName : vie.control.layer.controller.external.receiveView
 * @Description : 移动端用户相关接口
 * @Version : 1.0
 * @Author : lpniu
 * @Date: 2021/3/11 19:31
 */
@RequestMapping(name = "用户管理", value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据机构ID获取当前机构所有非管理员的用户信息
     * @Author : lpniu
     */
    @RequestMapping(value = "/getUserByOrgId",method = RequestMethod.GET)
    public Object getUserByOrgId(@RequestParam(value = "xfTicket", required = true) String xfTicket, HttpSession session) throws Exception {
        Object obj = userService.getUserByOrgId(xfTicket,session);

        return obj;
    }


    /**
     * 获取应用信息
     * @Author :
     */
    @RequestMapping(value = "/getAppInfo",method = RequestMethod.GET)
    public Object getAppInfo(@RequestParam(value = "xfTicket", required = true) String xfTicket) throws Exception{
        Object appInfo = userService.getAppInfo();
        return appInfo;
    }

    /**
     * 根据appId和userId查询权限列表
     * @Author : lpniu
     */
    @RequestMapping(value = "/getAuthorities",method = RequestMethod.GET)
    public Object getAuthorities(@RequestParam(value = "xfTicket", required = true) String xfTicket,HttpSession session) throws Exception{
        Object obj = userService.getAuthorities(xfTicket,session);
        return obj;
    }
    /**
     * 根据应用编码和用户id查询角色列表信息
     */
    @RequestMapping(value = "/getRoleByAppCodeUserId",method = RequestMethod.GET)
    public Object getRoleByAppCodeUserId(@RequestParam(value = "xfTicket", required = true) String xfTicket,HttpSession session) throws Exception{
        Object obj = userService.getRoleByAppCodeUserId(xfTicket,session);
        return obj;
    }


    /**
     * 获取用户信息
     * @Author : lpniu
     * @param xfTicket
     * @param session
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public Object getUserInfo(@RequestParam(value = "xfTicket", required = true) String xfTicket, HttpSession session) throws Exception{
        Object userInfo = userService.getUserInfo(xfTicket, session);
        return userInfo;
    }


    /**
     * 免密登录
     * @Author : lpniu
     * @param passWord  密码
     * @param loginName 用户名
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Object login(@RequestParam(value = "loginName", required = true) String loginName,
                        @RequestParam(value = "passWord", required = true) String passWord,HttpSession session) throws Exception {

        String result = userService.login(loginName, passWord,session);
        return result;

    }

    /**
     * 登出
     * @Author : lpniu
     * @param xfTicket
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Object logout(@RequestParam(value = "xfTicket", required = false) String xfTicket, HttpSession session, HttpServletRequest request){
        session.removeAttribute(xfTicket);
        request.getSession().invalidate();
        return null;
    }
}