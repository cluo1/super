package com.luo.ftp;


import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2015/8/6.
 */
public class SftpHelper {

    private static String DEAFULT_REMOTE_CHARSET = "UTF-8";
    private static String DEAFULT_LOCAL_CHARSET = "UTF-8";
    private ChannelSftp sftp = null;
    private static SftpHelper instance = new SftpHelper();
    private Session sshSession=null;
    private Channel channel = null;

    public static SftpHelper getInstance() {
        return instance;
    }

    public boolean connect(String host, int port, String username, String password) throws IOException {
        try
        {
            JSch jsch = new JSch();
//            jsch.getSession(username, host, port);
            if (null==sshSession) {
                sshSession = jsch.getSession(username, host, port);
                sshSession.setPassword(password);
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshSession.setConfig(sshConfig);
            }
            sshSession.connect();
            if (null==channel){
                channel = sshSession.openChannel("sftp");
            }
            channel.connect();
            sftp = (ChannelSftp) channel;
            return  true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        disconnect();
        return  false;
    }


    /**
     *上传文件到远程服务器，支持断点续传
     * @param localFilePath 本地文件路径
     * @param remoteDir 远程文件的文件夹
     * @param remoteFileName 远程文件
     * @return
     * @throws IOException
     */
    public synchronized UploadStatus upload(String localFilePath, String remoteDir, String remoteFileName) throws IOException {
        File file = new File(localFilePath);
        try ( FileInputStream  in = new FileInputStream(file)){
            // 创建服务器远程目录结构，创建失败直接返回
            if (createDir(remoteDir)== UploadStatus.Create_Directory_Fail) {
                return UploadStatus.Create_Directory_Fail;
            }
            sftp.put(in, remoteFileName);
            return UploadStatus.Upload_New_File_Success;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        return UploadStatus.Upload_New_File_Failed;
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        if(sftp!=null&&sftp.isConnected()){
            sftp.disconnect();
            sftp=null;
        }
        if (channel != null) {
            channel.disconnect();
            channel = null;
        }
        if (null!=sshSession){
            sshSession.disconnect();
            sshSession = null;
        }
    }

    /**
     * 创建目录
     * @param createpath
     * @return
     */
    public synchronized UploadStatus  createDir(String createpath)
    {
        UploadStatus status = UploadStatus.Create_Directory_Success;
        try
        {
            if (isDirExist(createpath))
            {
                this.sftp.cd(createpath);
                return status;
            }
            String pathArry[] = createpath.split("/");
         //   StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry)
            {
                if (path.equals(""))
                {
                    continue;
                }
               // filePath.append(path + "/");
                if (isDirExist(path))
                {
                    sftp.cd(path);
                }
                else
                {
                    // 建立目录
                    sftp.mkdir(path);
                    // 进入并设置为当前目录
                    sftp.cd(path);
                }

            }
            this.sftp.cd(createpath);
            return status;
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        status = UploadStatus.Create_Directory_Fail;
        return status;
    }


    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory)
    {
        boolean isDirExistFlag = false;
        try
        {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e)
        {
            if (e.getMessage().toLowerCase().equals("no such file"))
            {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }



}

