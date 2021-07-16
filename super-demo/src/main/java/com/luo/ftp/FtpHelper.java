package com.luo.ftp;

import com.luo.ftp.DownloadStatus;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2015/8/6.
 */
public class FtpHelper {

    private String hostname = "";
    private int port;
    private String username = "";
    private String pwcode = "";
    private static Logger logger = LoggerFactory.getLogger(FtpHelper.class);

    private static String DEAFULT_REMOTE_CHARSET = "UTF-8";
    private static String DEAFULT_LOCAL_CHARSET = "UTF-8";

    private static FtpHelper instance = new FtpHelper();

    public static FtpHelper getInstance() {
        return instance;
    }

    public boolean connect(FTPClient ftpClient, String hostname, int port, String username, String password) throws IOException {

        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.pwcode = password;

        ftpClient.connect(hostname, port);
        ftpClient.setControlEncoding(DEAFULT_REMOTE_CHARSET);
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            if (ftpClient.login(username, password)) {
                return true;
            }
        }
        disconnect(ftpClient);
        return false;
    }

    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     *
     * @param remoteFilePath 远程文件路径
     * @param localFilePath  本地文件路径
     * @return 上传的状态
     * @throws IOException
     */
    public DownloadStatus download(FTPClient ftpClient, String remoteFilePath, String localFilePath){
        DownloadStatus result = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 设置被动模式
            ftpClient.enterLocalPassiveMode();

            // 设置以二进制方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 检查远程文件是否存在
            FTPFile[] files = ftpClient.listFiles(new String(
                    remoteFilePath.getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET));

            if (files.length != 1) {
                //System.out.println("远程文件不存在");
                logger.info("远程文件不存在");
                return DownloadStatus.Remote_File_Noexist;
            }
            long lRemoteSize = files[0].getSize();
            File f = new File(localFilePath);
            // 本地存在文件，先删除，然后再重新下载
            if (f.exists()) {
                f.delete();
            }

            out = new FileOutputStream(f);
            in = ftpClient.retrieveFileStream(new String(remoteFilePath
                    .getBytes(DEAFULT_REMOTE_CHARSET), FTPClient.DEFAULT_CONTROL_ENCODING));
            byte[] bytes = new byte[10240000];
            long step = lRemoteSize / 100;
//            if (step <= 600){
//                System.out.println("远程文件过小，不予处理");
//                return DownloadStatus.Remote_File_Noexist;
//            }
            long process = 0;
            long localSize = 0L;
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localSize += c;
                long nowProcess = localSize / step;
                if (nowProcess > process) {
                    process = nowProcess;
                    if (process % 10 == 0) {
                        System.out.println("下载进度：" + process);
                    }
                    // TODO 更新文件下载进度,值存放在process变量中
                }
            }
            boolean upNewStatus = ftpClient.completePendingCommand();
            if (upNewStatus) {
                result = DownloadStatus.Download_New_Success;
            } else {
                result = DownloadStatus.Download_New_Failed;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 获取ftp远程文件大小
     * @param ftpClient ftp配置
     * @param remoteFilePath 远程文件位置
     * @return
     * @throws IOException
     */
    public long gainFtpFileSize(FTPClient ftpClient, String remoteFilePath) throws IOException{
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();

        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatus result;

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(
                remoteFilePath.getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET));

        if (files.length != 1) {
            System.out.println("远程文件不存在");
            return -1l;
        }
        return files[0].getSize();
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param file     上传文件
     * @param fileName 上传后的文件名
     * @return 上传结果
     * @throws IOException
     */
    public synchronized UploadStatus uploadFile(FTPClient ftpClient, File file, String fileName) throws IOException {
        // 设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding(DEAFULT_REMOTE_CHARSET);
        UploadStatus result;



        result = uploadFile(fileName, file, ftpClient, 0);
        return result;
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     *
     * @param localFilePath  本地文件名称，绝对路径
     * @param remoteFilePath 远程文件路径，使用/home/directory1/subdirectory/file.ext
     *                       按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return 上传结果
     * @throws IOException
     */
    public synchronized UploadStatus upload(FTPClient ftpClient, String localFilePath, String remoteFilePath) throws IOException {
        // 设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding(DEAFULT_REMOTE_CHARSET);
        UploadStatus result;
        // 对远程目录的处理
        remoteFilePath = remoteFilePath.replace("\\", "/");
        String remoteFileName = remoteFilePath;
        if (remoteFilePath.contains(File.separator) || remoteFilePath.contains("/")) {
            remoteFileName = new File(remoteFilePath).getName();
            // 创建服务器远程目录结构，创建失败直接返回
            if (createDirecroty(remoteFilePath, ftpClient) == UploadStatus.Create_Directory_Fail) {
                return UploadStatus.Create_Directory_Fail;
            }
        }
//        String  newPath  = CommandInjectionUtil.validFilePath(localFilePath);
        result = uploadFile(remoteFileName, new File(localFilePath), ftpClient, 0);
        return result;
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     */
    public void disconnect(FTPClient ftpClient) throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote    远程服务器文件绝对路径
     * @param ftpClient FTPClient对象
     * @return 目录创建是否成功
     * @throws IOException
     */
    public synchronized UploadStatus createDirecroty(String remote, FTPClient ftpClient)
            throws IOException {
        UploadStatus status = UploadStatus.Create_Directory_Success;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        if (!directory.equalsIgnoreCase("/")
                && !ftpClient.changeWorkingDirectory(new String(directory
                .getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET))) {
            // 如果远程目录不存在，则递归创建远程服务器目录
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end)
                        .getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET);
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录失败");
                        return UploadStatus.Create_Directory_Fail;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile  本地文件File句柄，绝对路径
     * @param ftpClient  FTPClient引用
     * @return
     * @throws IOException
     */
    public UploadStatus uploadFile(String remoteFile, File localFile,
                                                         FTPClient ftpClient, long remoteSize) throws IOException {


        UploadStatus status = UploadStatus.Upload_From_Break_Failed;
        // 显示进度的上传
        long step = (localFile.length() / 100) < 1 ? 1 : localFile.length() / 100;
        long process = 0;
        long localreadbytes = 0L;


        String remote = new String(remoteFile.getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET);



        try{
            try(  OutputStream out = ftpClient.storeFileStream(remote);
                  RandomAccessFile raf = new RandomAccessFile(localFile, "r")){
                if (out == null) {
                    String message = ftpClient.getReplyString();
                    throw new RuntimeException(message);
                }
                // 断点续传
                if (remoteSize > 0) {
                    ftpClient.setRestartOffset(remoteSize);
                    process = remoteSize / step;
                    raf.seek(remoteSize);
                    localreadbytes = remoteSize;
                }
                byte[] bytes = new byte[10240000];
                int c;
                while ((c = raf.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localreadbytes += c;
                    if (localreadbytes / step != process) {
                        process = localreadbytes / step;
                        System.out.println("上传进度:" + process);
                        // TODO 汇报上传状态
                    }
                }
                out.flush();
            }
            boolean result = ftpClient.completePendingCommand();
            if (remoteSize > 0) {
                status = result ? UploadStatus.Upload_From_Break_Success
                        : UploadStatus.Upload_From_Break_Failed;
            } else {
                status = result ? UploadStatus.Upload_New_File_Success
                        : UploadStatus.Upload_New_File_Failed;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }


    protected void makeRemoteDir(FTPClient ftp, String dir)
            throws IOException {
        String workingDirectory = ftp.printWorkingDirectory();
        if (dir.indexOf("/") == 0) {
            ftp.changeWorkingDirectory("/");
        }
        String subdir = new String();
        StringTokenizer st = new StringTokenizer(dir, "/");
        while (st.hasMoreTokens()) {
            subdir = st.nextToken();
            if (!(ftp.changeWorkingDirectory(subdir))) {
                if (!(ftp.makeDirectory(subdir))) {
                    int rc = ftp.getReplyCode();
                    if (((rc != 550) && (rc != 553) && (rc != 521))) {
                        throw new IOException("could not create directory: " + ftp.getReplyString());
                    }
                } else {
                    ftp.changeWorkingDirectory(subdir);
                }
            }
        }
        if (workingDirectory != null) {
            ftp.changeWorkingDirectory(workingDirectory);
        }
    }


    /**
     * 获取指定目录下的文件名称列表
     *
     * @param currentDir 需要获取其子目录的当前目录名称
     * @return 返回子目录字符串数组
     */
    public String[] GetFileNames(FTPClient ftpClient, String currentDir) {
        String[] dirs = null;
        try {
            if (currentDir == null)
                dirs = ftpClient.listNames();
            else
                dirs = ftpClient.listNames(currentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dirs;
    }

    /**
     * 获取指定目录下的文件与目录信息集合
     *
     * @param currentDir 指定的当前目录
     * @return 返回的文件集合
     */
    public FTPFile[] GetDirAndFilesInfo(FTPClient ftpClient, String currentDir) {
        FTPFile[] files = null;
        try {
            if (currentDir == null)
                files = ftpClient.listFiles();
            else
                files = ftpClient.listFiles(currentDir);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return files;
    }


    public List<FTPFile> GetDirInfo(FTPClient ftpClient, String currentDir) {
        List<FTPFile> list = new ArrayList<>();
        FTPFile[] files = null;
        try {
            if (currentDir == null)
                files = ftpClient.listFiles();
            else
                files = ftpClient.listFiles(currentDir);

            for (FTPFile ftpFile : files) {
                if (ftpFile.isDirectory()) {
                    list.add(ftpFile);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }


    public static String downloadStr(FTPClient ftpClient, String remoteFilePath)
            throws IOException {

        // 设置被动模式
        ftpClient.enterLocalPassiveMode();

        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(
                remoteFilePath.getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET));

        if (files.length != 1) {
            System.out.println("远程文件不存在");
            return null;
        }
        long lRemoteSize = files[0].getSize();
        StringBuffer stringBuffer = new StringBuffer();
        InputStream in = ftpClient.retrieveFileStream(new String(remoteFilePath
                .getBytes(DEAFULT_REMOTE_CHARSET), DEAFULT_LOCAL_CHARSET));
        byte[] bytes = new byte[1024];
        long step = lRemoteSize / 100;
        long process = 0;
        long localSize = 0L;
        int c;
        while ((c = in.read(bytes)) != -1) {
            stringBuffer.append(new String(bytes, 0, c));
            localSize += c;
            long nowProcess = localSize / step;
            if (nowProcess > process) {
                process = nowProcess;
                if (process % 10 == 0) {
                    System.out.println("下载进度：" + process);
                }
            }
        }
        in.close();
        boolean upNewStatus = ftpClient.completePendingCommand();
        if (upNewStatus) {
            return stringBuffer.toString();
        } else {
            return null;
        }
    }

    public void rename(FTPClient ftpClient, String from, String to) throws IOException {
        ftpClient.rename(from, to);
    }
}

