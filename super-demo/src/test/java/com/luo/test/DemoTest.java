package com.luo.test;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
//import org.bytedeco.javacpp.avcodec;
//import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class DemoTest {
    private static final Logger logger = LoggerFactory.getLogger(DemoTest.class);


    private static String TTRD_ROLLING_PREVIOUS_RULE_INSERT = "insert into ttrd_rolling_previous_rule " +
            "(ID, RULE, RULE_NAME, SQL_PATH, SERVICE_NAME, IS_FORCE, " +
            "JS_PATH, SQL_COLUMNS, SORT_ID, SQL_FILE, ROLLING_TYPE)\n" +
            "values (?,?,?,?,?,?,?,?,?,?,?);";

    private static final String JPG = "jpg";

    private static final String MP4_SUFFIX = ".mp4";

    @Test
    public void test3(){
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy年M月d日 HH:mm").format(date);
        logger.info(format);

    }


    @Test
    public  void  test() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        list.add("ABSaddaf");
        int len = 1;
        while (len<=11){
            list.add(len+"ABS");
            len++;
        }

        StringBuffer sb = new StringBuffer(TTRD_ROLLING_PREVIOUS_RULE_INSERT);
        int index = TTRD_ROLLING_PREVIOUS_RULE_INSERT.indexOf("?");

        for (int i = 0;i<list.size();i++){
            if(index != -1){
                sb.replace(index,index+1,"'"+list.get(i)+"'");
                index = sb.toString().indexOf("?");
            }else {
               throw  new Exception("参数个数有误");
            }
        }
        System.out.println(sb.toString());
        BufferedWriter bw = null;
        try {
             bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("e:/super/scripte.sql")), "UTF-8"));
             bw.write(sb.toString());
             bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test1(){
        String flowNoSeq = "123457";
        flowNoSeq = String.format("%07d",  Integer.valueOf(flowNoSeq));
        String s = "ZLS" + "" + flowNoSeq.substring(flowNoSeq.length() - 7, flowNoSeq.length());
        System.out.println(s);
    }



    /**
     * 截取视频获得指定帧的图片
     *
     * @param video   源视频文件
     * @param picPath 截图存放路径
     */
    public static void getVideoPic(File video, String picPath) {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();

            // 截取中间帧图片(具体依实际情况而定)
            int i = 0;
            int length = ff.getLengthInFrames();
            int middleFrame = length / 2;
            Frame frame = null;
            while (i < length) {
                frame = ff.grabFrame();
                if ((i > middleFrame) && (frame.image != null)) {
                    break;
                }
                i++;
            }

            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();

            // 对截图进行等比例缩放(缩略图)
            final int width = 480;
            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            File picFile = new File(picPath);
            ImageIO.write(thumbnailImage, JPG, picFile);

            ff.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws Exception {
        String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKuagdHLW30RxHJkE/tBbDLbCou5MAsaGlHZP0nuYY3+p/bkeSDIUcj+m8UNErmi0d1tGlJwA+HzHYPhsW70jKcCAwEAAQ==";
        String strMi = "JyWNHFgyMLrfVTIQMUDDe6xEUv+Tj4nI1ZHJLYDsIHbHxP4h9qB7UX2lKQYIevH9H2fmJrPAHVz3jX/Uv7KxdQ==";

        String pwd = ConfigTools.decrypt(key, strMi);
//        System.out.println(pwd);

        Map<String , Object> map = new HashMap<>();
        map.put("31","3");
        List<String> list = new ArrayList<String>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        list.add("11s");
        list.add("22a");
        list.add("3");
        list1.add(list);
        for (List<String> l:list1){
            boolean isExits = false;
            for (String str:l){
                if(map.get(str)!=null){
                    isExits = true;
                }
            }
            if (isExits){
                System.out.println("11");
            }

        }


    }

    @Test
    public void test2() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        String s = "";
              BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\admin\\Desktop\\mgck-master\\12.txt"), Charset.forName("GBK")));
        String line = null;
        try {
            while ((line = in.readLine()) != null) {
                String str = "insert into BS_sensitive_Words (SENSITIVE_WORD_ID, SENSITIVE_WORD_CONTENT, CREATE_DATE, CREATE_USER_NO, UPDATE_DATE, UPDATE_USER_NO,sensitive_type)\n" +
                        "values (('1'||to_char(sysdate, 'yyyymmdd')||LPAD(GLOBAL_ID.NEXTVAL, 8, '0')), '"+line+"', sysdate,'admin',  sysdate, 'admin','0');";

                String str2 = "insert into BS_sensitive_Words (SENSITIVE_WORD_ID, SENSITIVE_WORD_CONTENT, CREATE_DATE, CREATE_USER_NO, UPDATE_DATE, UPDATE_USER_NO,sensitive_type)\n" +
                        "values (('2'||to_char(sysdate, 'yyyymmdd')||LPAD(GLOBAL_ID.NEXTVAL, 8, '0')), '"+line+"', sysdate,'admin',  sysdate, 'admin','1');";

                sb.append(str2).append("\n").append("\n");
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

//        System.out.println(sb.toString());
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\mgck-master\\12_1.sql")), "UTF-8"));
            bw.write(sb.toString());
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test4() throws Exception {


        StringBuilder sb = new StringBuilder(" select ");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\chaoluo5\\Desktop\\new 2.txt"), Charset.forName("GBK")));
        String line = null;
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line).append(",");
            }
            sb.append(" callTime,saleStore,customerName");
            sb.append(" from dpsqczj");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sb.toString());
//        BufferedWriter bw = null;
//        try {
//            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\mgck-master\\12_1.sql")), "UTF-8"));
//            bw.write(sb.toString());
//            bw.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
