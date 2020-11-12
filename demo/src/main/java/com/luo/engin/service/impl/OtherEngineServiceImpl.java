package com.luo.engin.service.impl;

//import com.iflytek.proto.xsf.ReqData;
//import com.iflytek.proto.xsf.ResData;
//import com.iflytek.proto.xsf.Session;
//import com.iflytek.proto.xsf.XsfCallGrpc;
import com.luo.engin.service.EngineService;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 情绪识别、标签抽取序列标注、工单分类引擎语音分析
 */
@Service
public class OtherEngineServiceImpl implements EngineService {

    private static final Logger logger = LoggerFactory.getLogger(OtherEngineServiceImpl.class);


    @Override
    public String intrepret(String ip, int port, String sentence, String params, String userid) {

        logger.info("ip:{},port:{},sentence:{},params:{},userid:{}", new String[]{ip, String.valueOf(port), sentence, params, userid});
        String result = null;

//        ManagedChannel new_channel = null;
//        try {
//            new_channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext(true).build();
//            XsfCallGrpc.XsfCallBlockingStub blockingStub = XsfCallGrpc.newBlockingStub(new_channel);
//            //请求对象
//            ReqData.Builder builder = ReqData.newBuilder();
//            //设置操作
//            builder.setOp("Interpret");
//            //设置map参数
//            builder.putParam("sentence", sentence);
//            builder.putParam("params", params);
//            builder.putParam("usrid", userid);
//            //设置session
//            Session new_session = Session.getDefaultInstance();
//            builder.setS(new_session);
//            //转换成ReqData
//            ReqData reqData = builder.build();
//
//            ResData resData = null;
//
//            //接口调用
//            resData = blockingStub.call(reqData);
//
//            int code = resData.getCode();
//
//            if (code != 0) {
//                String errorInfo = resData.getErrorInfo();
//                logger.info("返回错误码:{}，错误信息：{}", new String[]{String.valueOf(code), errorInfo});
//
//            }
//
//            //结果获取
//            result = resData.getParamMap().get("result");
//
//            logger.info("result:{}", result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info("情绪识别、标签抽取序列标注、工单分类错误：{}", e);
//
//        } finally {
//            if (new_channel != null) {
//                new_channel.shutdown();
//            }
//        }
        return result;
    }
}
