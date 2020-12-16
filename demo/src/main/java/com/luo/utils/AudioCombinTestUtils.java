package com.luo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AudioCombinTestUtils {

//    private static final Logger logger = LoggerFactory.getLogger(AudioCombinUtils.class);

    public static void main(String[] args) throws Exception {
//        List<String> voiceNames = new ArrayList<>();
//        String filepath = "/home/iflytek/testAudio";
//        logger.info("=======filePath:{}=======", filepath);
//        String s = "[" +
//                "{\"status\":\"uploaded\",\"voiceId\":\"c1939723b737404e931f77056111c54f\",\"voiceName\":\"1.mp3\",\"voiceUri\":\"/1.wav\"}" +
//                ",{\"status\":\"uploaded\",\"voiceId\":\"c1939723b737404e931f77056111c54f\",\"voiceName\":\"2.mp3\",\"voiceUri\":\"/2.wav\"}" +
////				",{\"status\":\"uploaded\",\"voiceId\":\"c1939723b737404e931f77056111c54f\",\"voiceName\":\"test.wav\",\"voiceUri\":\"/4.V3\"}" +
//                "]";
//
//        List<LineTxtDto> lineTxtDtos = JSONArray.parseArray(s, LineTxtDto.class);
//
//        String voiceNameOld = lineTxtDtos.get(0).getVoiceName();
//        String[] voiceNameOlds = voiceNameOld.split("\\.");
//        String voiceNameNew = filepath + "/" + voiceNameOlds[0] + "1." + voiceNameOlds[1];
//
//        for (LineTxtDto l : lineTxtDtos) {
//            String voiceName = l.getVoiceName();
//            voiceNames.add(filepath + "/" + voiceName);
//        }
        List<String> filesPaths = new ArrayList<>();
        String filePathNew = "";

//        filePathNew = "/home/iflytek/testAudio/202012031557171.wav";
//
//        filesPaths.add("/home/iflytek/testAudio/20201203155717.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201203160253.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204150921.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204151325.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204151616.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204152256.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204171003.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204171441.wav");
//        filesPaths.add("/home/iflytek/testAudio/20201204171954.wav");

        filesPaths.add("E:/workspace/CARZJ/Source/advanced/1.mp3");
        filesPaths.add("E:/workspace/CARZJ/Source/advanced/2.mp3");
        filePathNew = "E:/workspace/CARZJ/Source/advanced/11.mp3";

        AudioCombinTestUtils r = new AudioCombinTestUtils();
        r.mergeAudio(filesPaths, filePathNew);
    }

    private void mergeAudio(List<String> filesPaths, String filePathNew) throws Exception {
        StringBuffer command = new StringBuffer();
        String tools = "ffmpeg";


        File f = new File(filePathNew);
        if (f.exists()){
            f.delete();
        }
        command.append(tools);

        for (int i = 0; i < filesPaths.size(); i++) {
            command.append(" -i ");
            command.append(filesPaths.get(i));
        }

        command.append(" -filter_complex [0:0][1:0]concat=n=");
        command.append(filesPaths.size());
        command.append(":v=0:a=1[a] -map [a] ");
        command.append(filePathNew);
        Executive(command.toString());
    }

    /**
     * 运行命令
     */

    private void Executive(String command) {
//        logger.info("ffmpeg执行合并音频命令：{}", command);
        System.out.println("ffmpeg执行合并音频命令======"+command);

        try {
            Process process = Runtime.getRuntime().exec(command);
//
            //防止死锁 开启两个线程
            new Thread() {
                @Override
                public void run() {
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
                    String line = null;
                    try {
                        while ((line = in.readLine()) != null) {
                            System.out.println("datax执行的结果为: " + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("GBK")));
                    String line = null;
                    try {
                        while ((line = err.readLine()) != null) {
                            System.out.println("error执行的结果为: " + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            err.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            int exitValue = process.waitFor();

            System.out.println("Returned value was：" + exitValue);
            process.destroy();

            if (exitValue == 0) {
                System.out.println("合并成功!");
            } else {
                System.out.println("合并失败！");
            }
            process.getOutputStream().close(); // 关闭

        } catch (Exception e) {

            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

}
