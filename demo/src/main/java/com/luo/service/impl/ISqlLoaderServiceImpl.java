package com.luo.service.impl;

import com.luo.service.ISqlLoaderService;
import com.luo.utils.TestSqlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ISqlLoaderServiceImpl implements ISqlLoaderService {
    private static final Logger LOGGER = LogManager.getLogger(ISqlLoaderServiceImpl.class);

    @Override
    public String getCtlTemplet(String filePath,String dataFileName,String tableName,String split,String filedName) {

        String strctl = "OPTIONS (skip=0,rows=800)\n" + // 0是从第一行开始  1是 从第二行
                " LOAD DATA\n" + //设置字符集编码SELECT * FROM NLS_DATABASE_PARAMETERS WHERE PARAMETER = 'NLS_CHARACTERSET';
                "INFILE '"+filePath+"/"+dataFileName+"'\n" +
//                " APPEND INTO TABLE "+tableName+" \n" + ////覆盖写入
                " TRUNCATE INTO TABLE "+tableName+"\n" + //清除写入
                " FIELDS TERMINATED BY '"+split+"'\n" + //数据中每行记录用","分隔 ,TERMINATED用于控制字段的分隔符，可以为多个字符。|需要转译
//                " OPTIONALLY  ENCLOSED BY \"'\"" + //源文件有引号 ''，这里去掉    ''''"
                " TRAILING NULLCOLS\n" +
                " "+filedName+" \n";  //表的字段没有对应的值时允许为空  源数据没有对应，写入null

        return strctl;
    }

    @Override
    public void ctlFileWriter(String strctl, String filePath, String ctlfileName) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath + "/" + ctlfileName+".ctl");
            fw.write(strctl);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.flush();
                fw.close();
            } catch (IOException e) {
                LOGGER.error("生成控制器文件异常...");
                e.printStackTrace();
            }
        }
    }

    @Override
    public String sqlloder(String userName, String passWord, String dataBase, String filePath, String ctlFileName, String dataFileName) {
        long start = System.currentTimeMillis();

        LOGGER.info("文件下载到服务器，开始sqlloder导入数据到Oracle数据库");

        //获取系统时间
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time=df.format(day);

        String logPath = filePath+"/log";
        File file = new File(logPath);
        if(!file.exists()){
            file.mkdirs();
        }
        /*"sqlldr username/password@Database control=filePath+ctlFileName.ctl data=dataFileName
         *     direct=true bindsize=20971520 errors=10000000 bad=filePath+/bad/++time+ ctlFileName+.bad log=filePath+/log/+time+ ctlFileName.log"
         */
        StringBuffer command = new StringBuffer();
        command.append("sqlldr ");
        command.append(userName);
        command.append("/");
        command.append(passWord);
        command.append("@");
        command.append(dataBase);
        command.append(" control=" + filePath +"/"+ ctlFileName+".ctl");
        command.append(" data=" + dataFileName);
        command.append(" direct=true");
        command.append(" bindsize=20971520");
        command.append(" errors=10000000");
        command.append(" bad=" + filePath+"/log/"+time+ ctlFileName+".bad");
        command.append(" log=" + filePath+"/log/"+time+ ctlFileName+".log");
        Executive(command.toString());

        long second = (System.currentTimeMillis() - start)/1000;
        LOGGER.info(dataFileName+"文件耗时:"+second+"秒");

        return "end";
    }

    /**
     *
     * 运行命令
     */
    private void Executive(String command) {

        String OS = System.getProperty("os.name").toLowerCase();
        String[] cmd=null;
        if(OS.contains("windows")) {
            cmd = new String[] { "cmd.exe", "/C", command }; // 命令
        }else {
            cmd = new String[] { "/bin/bash","-c", command }; // 命令
        }
        try {
            Process process = Runtime.getRuntime().exec(cmd);

            //防止死锁 开启两个线程
            new Thread() {
                @Override
                public void run() {
                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
                    String line = null;
                    try {
                        while ((line = in.readLine()) != null) {
                            LOGGER.info("datax执行的结果为: "+line);
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

            new Thread(){
                @Override
                public void run()
                {
                    BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("GBK")));
                    String line = null;
                    try
                    {
                        while((line = err.readLine()) != null)
                        {
                            LOGGER.info("error执行的结果为: "+line);
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            err.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            int exitValue = process.waitFor();

            LOGGER.info("Returned value was：" + exitValue);
            process.destroy();

            if (exitValue == 0) {
                LOGGER.info("导入成功!");
            } else {
                LOGGER.info("导入失败！");
            }
            process.getOutputStream().close(); // 关闭

        } catch (Exception e) {

            LOGGER.error(e.toString());
            e.printStackTrace();
        }
    }


}
