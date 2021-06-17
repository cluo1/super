/*
package com.luo.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.user.service.UserService;
import com.luo.user.utils.MD5Utils;
import com.luo.user.utils.OkHttpCLientUtils;
import com.luo.user.utils.RedisUtils;
import com.luo.user.utils.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;

*/
/**
 * @ClassName : com.iflytek.vie.app.provider.impl.user
 * @Description :
 * @Version : 1.0
 * @Author : lpniu
 * @Date: 2021/3/11 19:58
 *//*

@Service
public class UserServiceImpl implements UserService {

    @Value("${rest.server.url}")
    private String uapRestServerRrl;

    @Value("${cas.client.index}")
    private String serviceUrl;

    @Value("${app.code}")
    private String appCode;


    @Override
    public Object getAppInfo() throws IOException, Exception {

        String url = uapRestServerRrl+"app/get/byCode/"+appCode;
        String result = OkHttpCLientUtils.get(url);
        JSONObject obj = JSON.parseObject(result);
        if(!obj.getBoolean("flag")){
//            throw new Exception(ResultCode.UAP_APP_ERROR.getCode(), ResultCode.UAP_APP_ERROR.getMsg());
        }
        return obj.getJSONObject("data");
    }

    @Override
    public Object getAuthorities(String xfTicket,HttpSession session) throws Exception, IOException {
        //应用id
        JSONObject appInfo = (JSONObject)getAppInfo();
        String appId = appInfo.getString("id");
        //用户id
        JSONObject userInfo = (JSONObject)session.getAttribute(xfTicket);
        String userId =userInfo.getString("id");

        String url = uapRestServerRrl+"auth/get/byAppIdUserId?appId="+appId+"&userId="+userId;
        //redis key
        String result = null;
        String key = ResultCode.REDIS_KEY+"_"+url;
        if(RedisUtils.hasKey(key)&&RedisUtils.getString(key)!=null){
            result = RedisUtils.getString(key);
        } else{
            result = OkHttpCLientUtils.get(url);
            RedisUtils.setString(key,result,4*60*60);
        }
        JSONObject obj = JSON.parseObject(result);
        if(!obj.getBoolean("flag")){
//            throw new Exception(ResultCode.UAP_AUTH_ERROR.getCode(), ResultCode.UAP_AUTH_ERROR.getMsg());
        }
        return obj.getJSONArray("data");
    }

    @Override
    public Object getRoleByAppCodeUserId(String xfTicket,HttpSession session) throws IOException, Exception {

        //用户id
        JSONObject userInfo = (JSONObject)session.getAttribute(xfTicket);
        String userId =userInfo.getString("id");
        String url = uapRestServerRrl+"role/get/byAppCodeUserId?appCode="+appCode+"&userId="+userId;
        //redis key
        String result = null;
        String key = ResultCode.REDIS_KEY+"_"+url;
        if(RedisUtils.hasKey(key)&&RedisUtils.getString(key)!=null){
            result = RedisUtils.getString(key);
        } else{
            result = OkHttpCLientUtils.get(url);
            RedisUtils.setString(key,result,4*60*60);
        }
        JSONObject obj = JSON.parseObject(result);
        if(!obj.getBoolean("flag")){
//            throw new Exception(ResultCode.UAP_ROLE_ERROR.getCode(), ResultCode.UAP_ROLE_ERROR.getMsg());
        }
        return obj.getJSONArray("data");
    }

    @Override
    public Object getUserByOrgId(String xfTicket, HttpSession session) throws Exception {
        // 1、获取当前用户的机构id
        JSONObject userInfo = (JSONObject)session.getAttribute(xfTicket);
        String orgId =userInfo.getString("orgId");
        //2、获取当前机构下的所有用户集合
        String url = uapRestServerRrl+"user/get/list/byOrgId?orgId="+orgId+"&pageNum="+1+"&pageSize="+100;
        //redis key
        String result = null;
        String key = ResultCode.REDIS_KEY+"_"+url;
        if(RedisUtils.hasKey(key)&&RedisUtils.getString(key)!=null){
            result = RedisUtils.getString(key);
        } else{
            result = OkHttpCLientUtils.get(url);
            RedisUtils.setString(key,result,4*60*60);
        }
        JSONObject obj = JSON.parseObject(result);
        if(!obj.getBoolean("flag")){
//            throw new Exception(ResultCode.UAP_ORG_USER_ERROR.getCode(), ResultCode.UAP_ORG_USER_ERROR.getMsg());
        }
        //3、查询出所有用户的角色，剔除管理员角色编码的用户
        JSONArray jsonArray = obj.getJSONArray("data");
        JSONArray jsonArrayResult = new JSONArray();
        if(jsonArray!=null&&jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if(!object.getString("id").equals(userInfo.getString("id"))){
                    jsonArrayResult.add(object);
                }
            }
        }
        return jsonArrayResult;
    }

    @Override
    public Object getUserInfo(String xfTicket, HttpSession session) throws Exception {
        //用户信息
        JSONObject user = (JSONObject)session.getAttribute(xfTicket);
        //获取角色信息
        Object role = getRoleByAppCodeUserId(xfTicket, session);
        user.put("role",role);
        return user;
    }

    @Override
    public String login(String loginName, String passWord, HttpSession session) throws Exception {
        //1、免密登录测试 用户明是否正确
        String url = uapRestServerRrl+"ticket/getTicket/"+loginName+"/"+appCode;
        String result = OkHttpCLientUtils.get(url);
        JSONObject obj = JSON.parseObject(result);
        if(!obj.getBoolean("flag")){
            throw new Exception();
        }
        //2、根据用户获取用户信息
        //2.1 密码要进行加密
        String pwd = MD5Utils.md5(passWord);
//        ResultDto resultDto = UapServiceContext.getValidateService().loginByLoginNamePassWord(loginName,pwd);
//        if(!resultDto.isFlag()){
//            throw new Exception();
//        }
        String ticket = obj.getString("data");
        //将ticket放入session
        //以秒为单位，即在没有活动120分钟后，session将失效
        session.setMaxInactiveInterval(2*60*60);
//        session.setAttribute(ticket,resultDto.getData());
        return ticket;
    }
}*/
