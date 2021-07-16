package com.luo.ftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by admin on 2015/8/19.
 */
public class SFtpUtil {

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;
    private static final int TIME_OUT = 100000;

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public boolean connect(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(TIME_OUT);
            channel = session.openChannel("sftp");
            channel.connect(TIME_OUT);
            channelSftp = (ChannelSftp)channel;
        } catch (Exception e) {
            e.printStackTrace();
            channelSftp = null;
        }
        return channelSftp != null;
    }

    public void disconnect() {
        if (channelSftp != null) {
            channelSftp.exit();
        }
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    /**
     * 上传文件
     */
    public boolean upload(String directory, String uploadFile) {
        FileInputStream fileInputStream = null;
        try {
            checkNotNull(channelSftp);
            directory = directory.replace("\\", "/");
            channelSftp.mkdir(directory);
            channelSftp.cd(directory);


//            String  newPath  = CommandInjectionUtil.validFilePath(uploadFile);
            File file = new File(uploadFile);
            fileInputStream = new FileInputStream(file);
            channelSftp.put(fileInputStream, file.getName());

            System.out.println("上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(fileInputStream!=null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

//    private boolean mkDir(String dirName) {
//        dirName = dirName.replace("\\", "/");
//        String[] dirs = dirName.split("/");
//        try {
//            for (int i = 0; i < dirs.length; i++) {
//                try {
//                    sftp.cd(dirs[i]);
//                } catch (SftpException sException) {
//                    if (sftp.SSH_FX_NO_SUCH_FILE == sException.id) {
//                        sftp.mkdir(dirs[i]);
//                        sftp.cd(dirs[i]);
//                    }
//                }
//            }
//        } catch (SftpException e) {
//            System.out.println("mkDir Exception : " + e);
//        }
//    }

    /**
     * 下载文件
     */
    public boolean download(String directory, String downloadFile, String saveFile) {
        try {
            checkNotNull(channelSftp);
            directory = directory.replace("\\", "/");
            channelSftp.cd(directory);
            File file = new File(saveFile);
            try( FileOutputStream fos = new FileOutputStream(file)){
                channelSftp.get(downloadFile,fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 下载文件
     */
    public DownloadStatus vqiDownload(String directory, String downloadFile, String saveFile) {
        DownloadStatus result = DownloadStatus.Download_New_Success;;
        try {
            checkNotNull(channelSftp);
            directory = directory.replace("\\", "/");
            channelSftp.cd(directory);
            File file = new File(saveFile);
            try(FileOutputStream fos = new FileOutputStream(file)) {
                channelSftp.get(downloadFile, fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = DownloadStatus.Download_New_Failed;
        }
        return result;
    }
    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException {
        return sftp.ls(directory);
    }

    public static void main(String[] args) {
    }
}