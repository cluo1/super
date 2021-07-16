package com.luo.ftp;

import com.luo.ftp.FtpConfigVO;

/**
 * Created by Administrator on 2015/8/27.
 */
public interface AbstractFtp {

    public boolean connect(String hostname, int port, String username,String password) throws Exception;

    public boolean download(FtpConfigVO ftpConfig, String remoteFile, String localFile) throws Exception ;

    public boolean upload(FtpConfigVO ftpConfig, String localFile, String remoteFile) throws Exception;

}
