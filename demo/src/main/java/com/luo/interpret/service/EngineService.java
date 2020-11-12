package com.luo.interpret.service;

/**
 * 引擎解析语音文本接口
 */
public interface EngineService {

    public String intrepret(String ip,int port,String sentence, String params, String userid);

}
