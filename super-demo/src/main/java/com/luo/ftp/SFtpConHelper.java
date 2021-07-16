package com.luo.ftp;

import com.luo.ftp.FtpConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Administrator on 2015/8/27.
 */
public class SFtpConHelper implements AbstractFtp {

    vie.control.layer.ftp.SFtpUtil sFtpUtil = new vie.control.layer.ftp.SFtpUtil();

    private static Logger logger = LoggerFactory.getLogger(SFtpConHelper.class);

    @Override
    public boolean connect(String hostname, int port, String username, String password) {
        boolean ret = sFtpUtil.connect(hostname, port, username, password);
        sFtpUtil.disconnect();
        return ret;
    }

    @Override
    public boolean download(FtpConfigVO ftpConfig, String remoteFile, String localFile) throws Exception {
        boolean ret = sFtpUtil.connect(ftpConfig.getHostName(),
                ftpConfig.getPort(),
                ftpConfig.getUserName(),
                ftpConfig.getPwd());
        if (ret) {
            boolean status = sFtpUtil.download(new File(remoteFile).getParent(), new File(remoteFile).getName(), localFile);
            sFtpUtil.disconnect();
            return status;
        }
        return false;
    }

    @Override
    public boolean upload(FtpConfigVO ftpConfig, String localFile, String remoteFile) throws Exception {
        boolean ret = sFtpUtil.connect(ftpConfig.getHostName(), ftpConfig.getPort(), ftpConfig.getUserName(), ftpConfig.getPwd());
        if (ret) {
            boolean status = sFtpUtil.upload(new File(remoteFile).getParent(), localFile);
            sFtpUtil.disconnect();
            return status;
        }
        return false;
    }
}
