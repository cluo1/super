package com.luo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class JSONUtil {
    public static String objectToJson(Object o){

        return JSON.toJSONString(o);
    }

    public static Object jsonToObject(String jsonStr,Class c){
        return JSON.parseObject(jsonStr, c);
    }


    public static List jsonToList(String jsonStr, TypeReference t){
        return (List) JSON.parseObject(jsonStr, t);
    }
}
