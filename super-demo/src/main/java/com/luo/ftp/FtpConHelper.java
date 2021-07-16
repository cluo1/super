package com.luo.ftp;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by Administrator on 2015/8/27.
 */
public class FtpConHelper implements AbstractFtp {

    private FTPClient ftpClient = new FTPClient();

    @Override
    public boolean connect(String hostname, int port, String username, String password) throws Exception {
        return FtpHelper.getInstance().connect(new FTPClient(), hostname, port, username, password);
    }

    @Override
    public boolean download(FtpConfigVO ftpConfig, String remoteFile, String localFile) throws Exception {
        ftpClient.connect(ftpConfig.getHostName(), ftpConfig.getPort());
        ftpClient.login(ftpConfig.getUserName(), ftpConfig.getPwd());
        boolean ret =FtpHelper.getInstance().download(ftpClient, remoteFile, localFile)== DownloadStatus.Download_New_Success;
        ftpClient.logout();
        FtpHelper.getInstance().disconnect(ftpClient);
        return ret;
    }

    @Override
    public boolean upload(FtpConfigVO ftpConfig,String localFile, String remoteFile) throws Exception {
        ftpClient.connect(ftpConfig.getHostName(), ftpConfig.getPort());
        ftpClient.login(ftpConfig.getUserName(), ftpConfig.getPwd());
        boolean ret = FtpHelper.getInstance().upload(ftpClient, localFile, remoteFile)== UploadStatus.Upload_New_File_Success;
        ftpClient.logout();
        FtpHelper.getInstance().disconnect(ftpClient);
        return ret;
    }
}
