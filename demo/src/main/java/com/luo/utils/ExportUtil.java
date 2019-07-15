/**
 * 项目名称:starter-joyin
 * 类名称:ImportOrExportUtil.java
 * 包名称:com.joyintech.utils.importorexport
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  2018年9月17日       ShenYaFei         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.luo.utils;


import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.util.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportUtil<T> {

    /**
     * excel 导出公用 
     * @param title 表格标题名
     * @param headers 表格属性列名数组 （第一行标题）
     * @param Col 需要显示的表格属性列名数组(要展示的字段如CUST_NAME)
     * @param list 需要显示的数据集合
     * @param FileName 导出的文件名
     * @param rs 响应
     */
    public void ExcelExport(String title, String[] headers, String[] Col, List<T> list, HttpServletResponse rs, HttpServletRequest rq,
                            String FileName) {

        // 如果没有数据 则不往下执行
        if (list.size()==0) {
            return;
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(30);
        // 生成一个样式 并设置表头居中
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        int Cell = 0;
        for (short i = 0; i<headers.length; i++) {
            XSSFCell cell = row.createCell(Cell);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            Cell++;
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            String[] fields = Col;
            Cell = 0;
            for (short i = 0; i<fields.length; i++) {
                String fieldName = fields[i];
                XSSFCell cell = row.createCell(Cell);
                cell.setCellStyle(style);
                try {
                    Object value = "";
                    Class tCls = null;
                    Map map = null;
                    if (t instanceof Map) {
                        map = (Map)t;
                        value = map.get(fieldName);
                    } else {
                        String getMethodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
                        tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        value = getMethod.invoke(t, new Object[] {});
                    }
                    if (value==null) {
                        value = "";
                    }
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date)value;
                        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm");
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue!=null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            cell.setCellValue(textValue);
                        }
                    }
                    Cell++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // excel文件下载
        Export(rs, rq, FileName, workbook);
    }

    /**
     * 文件下载
     * @param rs 请求
     * @param rq 响应
     * @param FileName 文件名
     * @param workbook excel文件
     * @return
     */
    public static String Export(HttpServletResponse rs, HttpServletRequest rq, String FileName, XSSFWorkbook workbook) {
        OutputStream ouputStream = null;
        try {
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
            String dt = sd.format(date);
            // 导出的文件名
            String filename = FileName+dt+".xlsx";
            // 文件名编码
            filename = encodeFileName(rq, filename);
            // 设置响应头
            rs.setHeader("Content-Disposition", "attachment;filename="+filename);
            ouputStream = rs.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @Title: encodeFileName   
     * @Description: 导出文件名编码设置 
     * @param: @param request
     * @param: @param fileName
     * @param: @return
     * @param: @throws UnsupportedEncodingException      
     * @return: String      
     * @throws
     */
    public static String encodeFileName(HttpServletRequest request, String fileName)
        throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");

        if (null!=agent&&-1!=agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null!=agent&&-1!=agent.indexOf("Mozilla")) {
            return "=?UTF-8?B?"+(new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))))+"?=";
        } else {
            return fileName;
        }
    }
}
