package com.luo.interpret.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EngineProcess {

    @Qualifier("otherEngineServiceImpl")
    @Autowired
    private EngineService otherEngineService;

    @Qualifier("dialogSummaryServiceImpl")
    @Autowired
    private EngineService dialogSummaryService;

    public String enginVoiceAnalyize(){

        String ip = "172.31.223.38";
        int port;

        String sentence = "你好喂你好就在那个琅西路和那个啊北二环和那个北二环和在哪条路的交叉口啊东二环跟这个呃东二环那个那个长江东大街怎么了交通顺畅吗喂咱家东北二环与长江东大街我这车子刚刚挂了噢";
        String userid ="super";
        String params = "";

        //工单分类
        port = 10017;
        params = "nbest=5;org=074;username=unicom";
        String woCls = otherEngineService.intrepret(ip, port, sentence, params, userid);
        Map woClsMap = JSON.parseObject(woCls, Map.class);

        //标签抽取
        port = 20038;
        params = "username=unicom;org=1001;request_type=0";
        String tagExcTract = otherEngineService.intrepret(ip, port, sentence, params, userid);
        Map tagExcTractMap = JSON.parseObject(tagExcTract, Map.class);

        //情绪
        port = 10091;
        params = "org=1001;username=unicom;iemo_confidence_min=0.9;is_get_vector=0";
        String emotion = otherEngineService.intrepret(ip, port, sentence, params, userid);
        Map emotionMap = JSON.parseObject(emotion, Map.class);

        //工单总结
        params = "org=1001;username=unicom;entity_swich=false;summary_swich=false";
        String dialogSum = dialogSummaryService.intrepret("", 0, sentence, params, userid);
        Map dialogSumMap = JSON.parseObject(dialogSum, Map.class);

        return "";
    }
}
