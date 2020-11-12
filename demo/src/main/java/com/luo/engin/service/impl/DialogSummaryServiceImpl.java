package com.luo.engin.service.impl;

//import com.iflytek.isc.client.ISCApi;
import com.luo.engin.service.EngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 工单要素&工单总结引擎语音分析
 */
@Service
public class DialogSummaryServiceImpl implements EngineService {

    private static final Logger logger = LoggerFactory.getLogger(DialogSummaryServiceImpl.class);

//    private static final ISCApi instance;
//    private static final int init;

//    static {
//        instance = ISCApi.instance();
//        init = instance.init();
//        logger.info("init初始化:{}", init);
//    }

    @Override
    public String intrepret(String ip, int port, String sentence, String params, String userid) {
        String isc = "";

//        ISCApi instance = ISCApi.instance();
//        logger.info("params:{},userid:{},sentence:{}", new String[]{params, userid, sentence});
//
//        try {
//            isc = instance.interpret(sentence, params, userid);
//
//            logger.info("isc:{}", isc);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("工单要素提取错误信息：{}", e);
//        } finally {
//            if (instance != null) {
//                int fini = instance.fini();
//                logger.info("fini结束:{}", fini);
//            }
//
//        }
        return isc;
    }
}
