package com.luo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSqlLoader {
    private static final Logger LOGGER = LogManager.getLogger(TestSqlLoader.class);

    /**
     *
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param database 数据库地址
     * @param ctlFileDir 控制文件路径
     * @param ctlFileName 控制文件路径名称
     * @param dataFileName 数据文件绝对路径
     * @return the sql loader command
     */
    public  void sqlloder(String username, String password,
                          String database, String ctlFileDir, String ctlFileName,String dataFileName) {

        LOGGER.info("文件下载到服务器，开始sqlloder导入数据到Oracle数据库");

        //获取系统时间
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String time=df.format(day);

        /*"sqlldr username/password@Database control=ctlFileDir+tableName.ctl data=dataFileDir+dataFileName
         *     direct=true bindsize=20971520 errors=10000000 bad=ctlFileDir+/bad/++time+ tableName+.bad log=ctlFileDir+/log/+time+ tableName.log"
         */
        StringBuffer command = new StringBuffer();
        command.append("sqlldr ");
        command.append(username);
        command.append("/");
        command.append(password);
        command.append("@");
        command.append(database);
        command.append(" control=" + ctlFileDir +"\\"+ ctlFileName+".ctl");
        command.append(" data=" + dataFileName);
        command.append(" direct=true");
        command.append(" bindsize=20971520");
        command.append(" errors=10000000");
        command.append(" bad=" + ctlFileDir+"/bad/"+time+ ctlFileName+".bad");
        command.append(" log=" + ctlFileDir+"/log/"+time+ ctlFileName+".log");
        Executive(command.toString());

    }

    /**
     *
     * 运行命令
     */

    public static void Executive(String command) {

        // 获取子进程的输入流
        InputStream ins = null;
        // 获取子进程的错误流
        InputStream inserr = null;

        String OS = System.getProperty("os.name").toLowerCase();

        String[] cmd=null;
        if(OS.contains("windows")) {

            cmd = new String[] { "cmd.exe", "/C", command }; // 命令
        }else {
            cmd = new String[] { "/bin/bash","-c", command }; // 命令
        }


        try {
            Process process = Runtime.getRuntime().exec(cmd);
            inserr = process.getErrorStream(); // 获取执行cmd命令后的错误信息


            BufferedReader reader = new BufferedReader(new InputStreamReader(inserr, Charset.forName("GBK")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                LOGGER.info("执行命令错误：" + line); // 输出
            }

            ins = process.getInputStream(); // 获取执行cmd命令后的信息

            reader = new BufferedReader(new InputStreamReader(ins, Charset.forName("GBK")));

            while ((line = reader.readLine()) != null) {
                LOGGER.info("执行cmd命令后的信息："+line); // 输出
            }

            int exitValue = process.waitFor();

            LOGGER.info("Returned value was：" + exitValue);
            process.destroy();

            if (exitValue == 0) {
                LOGGER.info("导入成功!");
            } else {
                LOGGER.info("导入失败！");
            }

            process.getOutputStream().close(); // 关闭
            ins.close();
            inserr.close();
        } catch (Exception e) {

            LOGGER.error(e.toString());
            e.printStackTrace();
        }
    }


    public void ctlFileWriter(String fileRoute, String fileName, String tableName, String fieldName,String ctlfileName) {
        FileWriter fw = null;
        String strctl = "OPTIONS (skip=0,rows=1000)" + // 0是从第一行开始  1是 从第二行
                " LOAD DATA CHARACTERSET AL32UTF8 " + //设置字符集编码SELECT * FROM NLS_DATABASE_PARAMETERS WHERE PARAMETER = 'NLS_CHARACTERSET';
                "INFILE '"+fileRoute+""+fileName+"'" +
                " APPEND INTO TABLE "+tableName+"" + ////覆盖写入
                " FIELDS TERMINATED BY '\\|'" + //数据中每行记录用","分隔 ,TERMINATED用于控制字段的分隔符，可以为多个字符。|需要转译
//                " OPTIONALLY  ENCLOSED BY \"'\"" + //源文件有引号 ''，这里去掉    ''''"
                " TRAILING NULLCOLS "+fieldName+"";  //表的字段没有对应的值时允许为空  源数据没有对应，写入null
        try {
            fw = new FileWriter(fileRoute + "" + ctlfileName);
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

    public static void main(String[] args) {
        TestSqlLoader testSqlLoader = new TestSqlLoader();
        String userName = "iwssdev";
        String password = "iwssdev";
        String database = "192.168.90.237/orcl.zhang";
        String ctlFileDir = "H:/document/zy";
        String ctlFileName = "20151231";
//        String dataFileName = "H:/document/zy/users_data.csv";
        String dataFileName = "H:/document/zy/user.txt";

        testSqlLoader.sqlloder(userName, password,database, ctlFileDir, ctlFileName,dataFileName);
    }
}
