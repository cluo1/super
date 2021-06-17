package com.luo.user.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description spring上下文工作类
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    /**
     * 所有的配置信息
     */
    private static MutablePropertySources mutablePropertySources;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
        AbstractEnvironment aEnv = (AbstractEnvironment) applicationContext.getEnvironment();
        mutablePropertySources = aEnv.getPropertySources();
        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext=" + SpringBeanUtil.applicationContext + "========");
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static String getProperty(String name) {
    	return getApplicationContext().getEnvironment().getProperty(name);
    }
    
    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static String getProperty(String name, String defaultValue) {
    	String propertyValue = getApplicationContext().getEnvironment().getProperty(name);
    	if(StringUtils.isBlank(propertyValue)) {
    		return defaultValue;
    	}else {
    		return propertyValue;
    	}
    }

    /**
     * 获取前缀的参数map
     */
    public static Map<String, String> getCfgMapByprefix(String prefix) {
        Map<String, String> map = new HashMap<String, String>();
        mutablePropertySources.forEach(propertySource -> {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mps = (MapPropertySource) propertySource;
                Set<String> keys = mps.getSource().keySet();
                for (String key : keys) {
                    if (key.startsWith(prefix)) {

                        map.put(key, String.valueOf(mps.getProperty(key)));
                    }
                }
            }
        });

        return map;
    }
    
    /**
	 * 
	 * 解析具有固定名称的配置项放到前端配置信息json中
	 * @lastModified
	 * @history
	 */
	/*public static String getAppConfig() {
		Map<String, String> map = getCfgMapByprefix("sqdj.attachment.");
		return JsonUtils.jsonObjectString(map);
	}*/

}
