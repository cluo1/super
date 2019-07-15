package com.luo.utils;

import java.lang.reflect.Method;

public class ReflectUtil {

    public static Object invokeMethod(String className, String methodName,Object[] params){
        Object o = null;
        try {
            Class aClass = getClass(className);

            for(Method method : aClass.getMethods()){
                if(method !=null && methodName.equals(method.getName())){
                    if(!method.isAccessible()){
                        method.setAccessible(true);
                    }
                    o = method.invoke(aClass.newInstance(), params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Class getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Object getObject(String className)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return getClass(className).newInstance();
    }
}
