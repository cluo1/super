package com.luo.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileToZipUtils {
    private static Logger log = LoggerFactory.getLogger(FileToZipUtils.class);

    public byte[] createZip(String srcSource) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        File file = new File(srcSource);
        createAllFile(zipOutputStream, file, "d:/");
        IOUtils.closeQuietly(zipOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void createAllFile(ZipOutputStream zipOutputStream, File file, String dir) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            zipOutputStream.putNextEntry(new ZipEntry(dir + File.separator));
            dir = dir.length() == 0 ? "" : dir + File.separator;
            for (int i = 0; i < files.length; i++) {
                createAllFile(zipOutputStream, files[i], dir + files[i].getName());
            }
        } else {
            zipOutputStream.putNextEntry(new ZipEntry(dir + File.separator));
            zipOutputStream.write(FileUtils.readFileToByteArray(file));
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
    }

    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (sourceFile.exists() == false) {
            log.info("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");
                if (zipFile.exists()) {
                    deleteDir(zipFile);
//                    log.info(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                }
                File[] sourceFiles = sourceFile.listFiles();
                if (null == sourceFiles || sourceFiles.length < 1) {
                    log.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                } else {
                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));
                    byte[] bufs = new byte[1024 * 10];
                    for (int i = 0; i < sourceFiles.length; i++) {
                        // 创建ZIP实体，并添加进压缩包ZipUtil
                        ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                        zos.putNextEntry(zipEntry);
                        // 读取待压缩的文件并写进压缩包里
                        fis = new FileInputStream(sourceFiles[i]);
                        bis = new BufferedInputStream(fis, 1024 * 10);
                        int read = 0;
                        while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                            zos.write(bufs, 0, read);
                        }
                    }
                    flag = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // 关闭流
                try {
                    if (null != bis)
                        bis.close();
                    if (null != zos)
                        zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    /**
     * 删除生成的临时文件
     *
     * @param file
     * @return
     */
    private static void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f:files){
                deleteDir(f);
            }
        }
        // 目录此时为空，可以删除
        file.delete();
    }

    public static void main(String[] args) {
        new FileToZipUtils().fileToZip("F:\\eclipse","d:/","z");
    }
}
