/**
 * 项目名称:sysman
 * 类名称:SysFileServiceImpl.java
 * 包名称:com.joyintech.sysman.service.impl
 *
 * 修改履历:
 *       日期                   修正者      主要内容
 *  Tue Aug 07 11:07:31 CST 2018       沈亚飞         初版做成
 *
 * Copyright (c) 2007-2018 兆尹科技
 */

package com.cluo.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cluo.entity.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Maps;
/*import com.joyintech.jdbc.Page;
import com.joyintech.syscomm.entity.SysFile;
import com.joyintech.sysman.dao.ISysFileDao;
import com.joyintech.sysman.dao.ISysFileHisDao;
import com.joyintech.sysman.service.ISysFileService;
import com.joyintech.sysman.utils.SysmanBaseException;
import com.joyintech.utils.date.DateUtil;*/


/**
 * SysFile管理业务基本实现类<br>
 * 对SysFile的增 删 改 查逻辑实现  <br>
 * @author 沈亚飞
 * @version 1.0
 */
@Service
public class SysFileServiceImpl{

//    @Value("${filepath}")
    private String filepath;

/*    *//**
     * 注入要操作的DAO
     *//*
    @Autowired
    private ISysFileDao iSysFileDao;

    *//**
     * 注入文件历史DAO
     *//*
    @Autowired
    private ISysFileHisDao iSysFileHisDao;*/

    /*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService
     * insertSysFile(com.joyintech.sysman.entity.SysFile)
     */
   /* public int insertSysFile(SysFile sysFile) {

        return iSysFileDao.insertSysFile(sysFile);

    }

    *//*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService
     * updateSysFile(com.joyintech.sysman.entity.SysFile)
     *//*
    
    public int updateSysFile(SysFile sysFile) {

        int i = iSysFileDao.updateSysFile(sysFile);

        if (1!=i) {
            throw new SysmanBaseException("JYN-10101", "更新SysFile异常", "JYN-10101:要更新的SysFile记录已不存在或无权限！", null);
        }

        return i;
    }

    *//*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService deleteSysFile(java.lang.String)
     *//*
    
    @Transactional
    public int deleteSysFile(String[] fileIds) {
        // 查询sysfiles
        List<SysFile> sysfiles = iSysFileDao.findByIds(Arrays.asList(fileIds));
        // 文件放入历史文件中
        int s = iSysFileHisDao.insertSysFileHis(sysfiles);
        // 删除文件
        int i = iSysFileDao.deleteSysFile(SysFileServiceImpl.toString(fileIds));

        if (sysfiles.size()!=i||1!=s||sysfiles.size()<=0) {
            throw new SysmanBaseException("JYN-10102", "删除SysFile异常", "JYN-10102:要删除的SysFile记录已不存在或无权限！", null);
        }

        return i;

    }*/

    /*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService findSysFileById(java.lang.String)
     */
    
    /*public SysFile findSysFileById(String fileId) {

        return iSysFileDao.findSysFileById(fileId);
    }

    *//*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService findPageSysFile(long, int)
     *//*
    
    public Page<SysFile> findPageSysFile(long currentPage, int pageSize, SysFile sysFile) {

        return iSysFileDao.findPageSysFile(currentPage, pageSize, sysFile);
    }*/

    /*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService findPageSysFile(long, int)
     */
    
    public Map<String, Object> uploadFiles(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        // 记录上传失败的文件
        StringBuilder ErrorFile = new StringBuilder("");
        String date = "";//DateUtil.convertDateToString(new Date(), "yyyyMMdd");
        try {
            // 先实例化一个文件解析器
            CommonsMultipartResolver coMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (coMultipartResolver.isMultipart(request)) {// request 存在文件
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                // 获得classId
                String classId = multiRequest.getParameter("classId");

                // 获得文件
                List<MultipartFile> files = multiRequest.getFiles("file");

                for (MultipartFile file: files) {// 遍历文件 取出单个文件
                    // 获取原始文件名
                    String fileName = file.getOriginalFilename();
                    String newfileName = new Date().getTime()+String.valueOf(fileName);

                    // 获取物理路径webapp所在路径
                    String pathRoot = filepath+"joyintech/upload/"+date;

                    FileOutputStream fos = null;
                    InputStream in = null;
                    File outDir = null;
                    Boolean flag = true;
                    try {
                        outDir = new File(pathRoot);
                        if (!outDir.getParentFile().exists()) {
                            outDir.getParentFile().mkdirs();
                        }
                        if (!outDir.exists()) {
                            outDir.mkdir();
                        }
                        // 创建输出流，用流将文件保存到指定目录
                        fos = new FileOutputStream(pathRoot+"/"+newfileName);
                        // 获得输入流
                        in = file.getInputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];// 创建缓冲区 1kb
                        while ((len = in.read(buffer))!=-1) {
                            fos.write(buffer, 0, len);// 每次写入1kb
                        }
                        fos.flush();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        flag = false;
                        map.put("success", false);
                        ErrorFile.append((ErrorFile.length()>0 ? "，" : "")+fileName+"上传失败");
                    } catch (IOException e) {
                        e.printStackTrace();
                        flag = false;
                        map.put("success", false);
                        ErrorFile.append((ErrorFile.length()>0 ? "，" : "")+fileName+"上传失败");
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false;
                        map.put("success", false);
                        ErrorFile.append((ErrorFile.length()>0 ? "，" : "")+fileName+"上传失败");
                    } finally {
                        if (in!=null) {
                            try {
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos!=null) {
                            try {
                                fos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (flag) {
                       /* SysFile sysFile = new SysFile();
                        sysFile.setFileId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                        sysFile.setFileName(fileName);
                        sysFile.setFileSize(outDir.length());
                        sysFile.setFileType(fileName.substring(fileName.lastIndexOf(".")+1));
                        sysFile.setClassId(classId);
                        sysFile.setFilePath(pathRoot+"/"+newfileName);
                        sysFile.setCreateUserNo("admin");
                        sysFile.setCreateDate(new Date());
                        sysFile.setUpdateUserNo("admin");
                        sysFile.setUpdateDate(new Date());
                        iSysFileDao.insertSysFile(sysFile);
                        map.put("success", true);*/
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            ErrorFile.append("上传失败");
        }
        if ((Boolean)map.get("success")) {
            map.put("msg", "上传成功");
        } else {
            map.put("msg", ErrorFile);
        }
        return map;
    }

    /*
     * (non-Javadoc)
     * @see com.joyintech.sysman.service.ISysFileService findPageSysFile(long, int)
     */
    
    public Map<String, Object> download(String[] fileids, HttpServletResponse response) {

        Map<String, Object> map = Maps.newHashMap();
        List<SysFile> sysfiles = null;//iSysFileDao.findByIds(Arrays.asList(fileids));
        if (sysfiles.size()>1) {// 多文件
            SysFileServiceImpl.downloadZipFile(sysfiles, response);
        } else {// 单文件
            map = SysFileServiceImpl.downloadFile(sysfiles.get(0), response);
        }

        return map;
    }

    /**
     * 数组 转为 '12','23'格式
     * @param a 数组
     * @return String 返回信息
     */
    public static String toString(Object[] a) {
        if (a==null) {
            return "''";
        }

        int iMax = a.length-1;
        if (iMax==-1) {
            return "''";
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0;; i++) {
            b.append("'");
            b.append(String.valueOf(a[i]));
            b.append("'");
            if (i==iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }

    /**
     * 单文件下载
     * @param sysfile SysFile类
     * @param response 请求
     * @return Map<String,Object> 返回信息
     */
    public static Map<String, Object> downloadFile(SysFile sysfile, HttpServletResponse response) {

        Map<String, Object> map = Maps.newHashMap();

        String filePath = sysfile.getFilePath();
        String fileName = sysfile.getFileName();

        // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 2.设置文件头：最后一个参数是设置下载文件名
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ServletOutputStream out;
        // 通过文件路径获得File对象
        File file = new File(filePath);

        try {
            FileInputStream inputStream = new FileInputStream(file);

            // 3.通过response获取ServletOutputStream对象(out)
            try {
                out = response.getOutputStream();
                int b = 0;
                byte[] buffer = new byte[512];
                while (b!=-1) {
                    b = inputStream.read(buffer);
                    // 4.写到输出流(out)中
                    out.write(buffer, 0, b);
                }
                inputStream.close();
                out.close();
                out.flush();
                map.put("success", true);
            } catch (IOException e) {
                e.printStackTrace();
                map.put("success", false);
                map.put("msg", "下载失败");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "文件不存在");
        }
        return map;
    }

    /**
     * 多文件打成zip包 下载
     * @param sysfiles 多个文件
     * @param response 请求
     * @return Map<String,Object> 返回信息
     */
    public static Map<String, Object> downloadZipFile(List<SysFile> sysfiles, HttpServletResponse response) {

        Map<String, Object> map = Maps.newHashMap();
        StringBuilder errorFile = new StringBuilder("");

        String zipName = "";//DateUtil.convertDateToString(new Date(), "yyyyMMdd")+".zip";

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="+zipName);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(response.getOutputStream());
            for (Iterator<SysFile> it = sysfiles.iterator(); it.hasNext();) {
                SysFile file = it.next();
                try {
                    ZipUtils.doCompress(file.getFilePath(), file.getFileName(), out);
                    response.flushBuffer();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    map.put("succsee", false);
                    errorFile.append("未找到").append(file.getFileName()).append("文件");
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("succsee", false);
                    errorFile.append(file.getFileName()).append("下载失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("succsee", false);
            errorFile.append("下载失败");
        } finally {
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

}


/**
 * 打包zip
 * @author Administrator
 *@version 1.0
 *
 */
class ZipUtils {

    private ZipUtils() {}

    /**
     * 打包
     * @param path 地址
     * @param fileName 文件名
     * @param out 输出流
     * @throws IOException 异常
     */
    public static void doCompress(String path, String fileName, ZipOutputStream out)
        throws IOException {
        doCompress(new File(path), fileName, out);
    }

    /**
     * 打包
     * @param file 文件流
     * @param fileName 文件名
     * @param out 输出流
     * @throws IOException 异常
     */
    public static void doCompress(File file, String fileName, ZipOutputStream out)
        throws IOException {
        doCompress(file, out, "", fileName);
    }

    /**
     * 打包
     * @param inFile 文件流
     * @param fileName 文件名
     * @param out 输出流
     * @param dir dir流
     * @throws IOException 异常
     */
    public static void doCompress(File inFile, ZipOutputStream out, String dir, String fileName)
        throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files!=null&&files.length>0) {
                for (File file: files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir+"/"+name;
                    }
                    ZipUtils.doCompress(file, out, name, fileName);
                }
            }
        } else {
            ZipUtils.doZip(inFile, out, dir, fileName);
        }
    }

    /**
     * 变成zip
     * @param inFile 文件
     * @param out 输入流
     * @param dir 文件夹
     * @param fileName 文件名
     * @throws IOException 异常
     */
    public static void doZip(File inFile, ZipOutputStream out, String dir, String fileName)
        throws IOException {
        String entryName = fileName;
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer))>0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

}
