package com.luo.filter;

import com.alibaba.fastjson.JSON;
import com.luo.user.utils.ResultCode;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author:
 * @Date: 2021/3/12 13:43
 * @params null
 * @return
 **/
public class LoginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    public static final String SEPARATOR = ",";
    private Set<String> excludesUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String param = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (param != null && param.trim().length() != 0) {
            this.excludesUrls = new HashSet(Arrays.asList(param.split(SEPARATOR)));
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpres = (HttpServletRequest) request;
        //请求接口地址
        String requestURI = httpres.getRequestURI().replace(httpres.getContextPath(),"");
        // 检查请求中是否含有 参数tikect ,校验tickt 是否存活
        String ticket = httpres.getParameter("xfTicket");
        logger.info("xfTicket ="+ticket);
        logger.info("session = "+((HttpServletRequest) request).getSession().getId());
        HttpSession session = httpres.getSession();
        Object obj = session.getAttribute(ticket);
        if (StringUtils.isNotBlank(ticket)&&obj!=null){
            chain.doFilter(request, response);
        } else if(this.isExclusion(requestURI)) {
            // 不过滤
            chain.doFilter(request, response);
        }  else{
            returnJson(response, JSON.toJSONString(ResultCode.UAP_TICKET_ERROR.getMsg()));
        }

    }

    public boolean isExclusion(String requestURI) {

        if (this.excludesUrls == null) {
            return false;
        }

        for (String url : this.excludesUrls) {
            if (url.equals(requestURI)) {
                return true;
            }
        }
        return false;
    }

    private void returnJson(ServletResponse response, String json) throws IOException{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}



