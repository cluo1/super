package com.cluo.test;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class DemoTest {
    private static String TTRD_ROLLING_PREVIOUS_RULE_INSERT = "insert into ttrd_rolling_previous_rule " +
            "(ID, RULE, RULE_NAME, SQL_PATH, SERVICE_NAME, IS_FORCE, " +
            "JS_PATH, SQL_COLUMNS, SORT_ID, SQL_FILE, ROLLING_TYPE)\n" +
            "values (?,?,?,?,?,?,?,?,?,?,?);";
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
}
