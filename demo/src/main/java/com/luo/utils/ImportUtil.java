/**
 * 项目名称:starter-joyin
 * 类名称:ImportUtil.java
 * 包名称:com.joyintech.utils.importorexport
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  2018年9月17日       ShenYaFei         初版做成
 *
 * Copyright (c) 2016-2017 兆尹科技
 */

package com.luo.utils;


import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * excel导入工具类
 * @author kf687
 * @version 1.0
 *
 */
public class ImportUtil {

    /**
     * 
     * @param file excel文件
     * @return List<List<Object>>
     * @throws IOException 
     */
    public static List<List<Object>> importExcel(File file)
        throws IOException {
        String fileName = file.getName();
        String extension = fileName.lastIndexOf(".")==-1 ? "" : fileName.substring(fileName.lastIndexOf(".")+1);
        if ("xls".equals(extension)) {
            return read2003Excel(file);
        } else if ("xlsx".equals(extension)) {
            return read2007Excel(file);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**  
     * 读取 office 2003 excel  
     * @param file file
     * @throws IOException   
     * @throws FileNotFoundException 
     * @return List<List<Object>> List<List<Object>>
     */
    private static List<List<Object>> read2003Excel(File file)
        throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hwb.getSheetAt(0);
        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = sheet.getFirstRowNum(); i<=sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row==null) {
                continue;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j<=row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell==null) {
                    continue;
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        // System.out.println(i+"行"+j+" 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        // System.out.println(i+"行"+j+" 列 is Number type ;
                        // DateFormt:"+cell.getCellStyle().getDataFormatString());
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        // System.out.println(i+"行"+j+" 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        // System.out.println(i+"行"+j+" 列 is Blank type");
                        value = "";
                        break;
                    default:
                        // System.out.println(i+"行"+j+" 列 is default type");
                        value = cell.toString();
                }
                if (value==null||"".equals(value)) {
                    continue;
                }
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }

    /**
     * 
     * @param file file
     * @return List<List<Object>> List<List<Object>>
     * @throws IOException IOException
     */
    private static List<List<Object>> read2007Excel(File file)
        throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        for (int i = sheet.getFirstRowNum(); i<=sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row==null) {
                continue;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j<=row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell==null) {
                    continue;
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        // System.out.println(i+"行"+j+" 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        // System.out.println(i+"行"+j+" 列 is Number type ;
                        // DateFormt:"+cell.getCellStyle().getDataFormatString());
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        // System.out.println(i+"行"+j+" 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        // System.out.println(i+"行"+j+" 列 is Blank type");
                        value = "";
                        break;
                    default:
                        // System.out.println(i+"行"+j+" 列 is default type");
                        value = cell.toString();
                }
                if (value==null||"".equals(value)) {
                    continue;
                }
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }

    /**
     * 判断导入的excel的sheet是不是模板
     * @param sheet 待判断的sheet页
     * @param headers 表头
     * @return
     */
    public static boolean isTemplate(Sheet sheet, String[] headers) {
        boolean flag = true;
        // 获取第一行表头信息
        Row row = sheet.getRow(0);
        if (row.getLastCellNum()!=headers.length) {
            flag = false;
        } else {
            for (int i = 0; i<headers.length; i++) {
                String cellValue = row.getCell(i).getStringCellValue();
                if (!cellValue.equals(headers[i])) {// 判断excel表头与表头是否一致
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 验证是否是Excel和判断excel的格式是否为2003
     * @param file excel文件
     * @return
     */
    public static boolean validateExcel(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension = filename.lastIndexOf(".")==-1 ? "" : filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
        if (!ExcelConstant.XLS.equals(extension)&&!ExcelConstant.XLSX.equals(extension)) {
            throw new RuntimeException("要上传的文件格式不正确！");
        }
        boolean isExcel2003 = true;
        if (ExcelConstant.XLSX.equals(extension)) {
            isExcel2003 = false;
        }
        return isExcel2003;
    }

    /**
     * 读取cell的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = null;
        if (cell!=null) {
            int i = cell.getCellType();

            if (i==0) {
                cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
            }
            if (i==1) {
                cellValue = cell.getStringCellValue();
            }
            if (i==3) {
                cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public String importWhiteList(MultipartFile[] files) {
        for (MultipartFile file: files) {
            String filename = file.getOriginalFilename();
            String extension = filename.lastIndexOf(".")==-1 ? "" : filename.substring(filename.lastIndexOf(".")+1);
            if (!"xls".equals(extension)&&!"xlsx".equals(extension)) {
//                throw new SysmanBaseException("", "上传文件异常", "文件解析失败", null);
            }
            boolean isExcel2003 = true;
            if ("xlsx".equals(extension)) {
                isExcel2003 = false;
            }
            Workbook wb = null;
            try {
                InputStream is = file.getInputStream();

                if (isExcel2003) {
                    wb = new HSSFWorkbook(is);
                } else {
                    wb = new XSSFWorkbook(is);
                }
            } catch (IOException e) {
//                throw new SysmanBaseException("", "导入异常", "导入异常", null);
            }
            Sheet sheet = wb.getSheetAt(0);
            System.out.println(sheet.getLastRowNum());
            if (sheet!=null) {
                String[] headers = {"白名单类型", "白名单"};
                boolean flag = ImportUtil.isTemplate(sheet, headers);
                if (!flag) {
//                    throw new SysmanBaseException("", "读取excel异常", "请按照正确的模板导入，导入失败！", null);
                }
                int num = sheet.getLastRowNum();
                if (num==0) {
//                    throw new SysmanBaseException("", "读取excel异常", "文件解析失败!", null);
                }
//                List<FclSysOpenBetaWhitelist> list = new ArrayList<FclSysOpenBetaWhitelist>();

                for (int r = 1; r<=sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row==null) {
                        continue;
                    }

                    String falseTitle = "";

//                    FclSysOpenBetaWhitelist sysOpenBetaWhitelist = new FclSysOpenBetaWhitelist();

                    Cell cell0 = row.getCell(0);
                    if (cell0!=null) {
                        String taName = getCellValue(cell0);// 白名单类型
//                        if (!StringUtils.isEmpty(taName)) {
//                            List<FclSysDictItem> fclSysDictItems = fclSysDictDao.validateDict("d220", taName);
//                            if (CollectionUtils.isEmpty(fclSysDictItems)) {
//                                falseTitle += "导入的第"+(r+1)+"行白名单类型不存在;";
//                            } else {
//                                sysOpenBetaWhitelist.setWhitelistType(fclSysDictItems.get(0).getDictItemVal());
//                            }
//
//                        }
                    }
                    Cell cell1 = row.getCell(1);
                    if (cell1!=null) {
                        String taName = getCellValue(cell1);// 白名单
//                        if (!StringUtils.isEmpty(taName)) {
//                            sysOpenBetaWhitelist.setWhitelistTxt(taName);
//                        }
                    } else {
                        falseTitle += "导入的第"+(r+1)+"行白名单不能为空;";
                    }

//                    if (!StringUtils.isEmpty(falseTitle)) {// 存在错误数据，抛异常
//                        throw new SysmanBaseException("", falseTitle, falseTitle, null);
//                    } else {
//                        List<FclSysOpenBetaWhitelist> whiteList = iFclSysOpenBetaWhitelistDao.findFclSysOpenBetaWhitelistList(sysOpenBetaWhitelist);
//                        if (whiteList.size()>0) {
//                            throw new SysmanBaseException("", "第"+(r+1)+"行数据已存在", "第"+(r+1)+"行数据已存在", null);
//                        }
//                    }
//                    sysOpenBetaWhitelist.setSerialNo(pageHelper.findGlobalIdFromOracle());
//
//                    list.add(sysOpenBetaWhitelist);
                }
            }
        }

        return "！";
    }

    /**
     * 读取cell的值
     * @param cell cell
     * @return String
     */
    public String getCellValue1(Cell cell) {
        String cellValue = null;
        if (cell!=null) {
            int i = cell.getCellType();

            if (i==HSSFCell.CELL_TYPE_NUMERIC) {// 数字类型（包含时间）
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date value = cell.getDateCellValue();
//                    cellValue = DateUtil.convertDateToString(value, "yyyy-MM-dd HH:mm:ss");
                } else {
                    cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                }

            }
            if (i==HSSFCell.CELL_TYPE_STRING) {// 字符串类型
                cellValue = cell.getStringCellValue();
            }
            if (i==HSSFCell.CELL_TYPE_BLANK) {// 空白的
                cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
