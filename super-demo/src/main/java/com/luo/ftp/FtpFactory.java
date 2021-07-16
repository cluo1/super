package com.luo.ftp;

import com.luo.ftp.FtpConHelper;

/**
 * Created by Administrator on 2015/8/27.
 */
public class FtpFactory {

    private static final String FILE_TRANS_FTP = "ftp";
    private static final String FILE_TRANS_SFTP = "sftp";

    public static AbstractFtp getFtpInstance() {
        return new FtpConHelper();
    }

    public static AbstractFtp getSFtpInstance() {
        return new SFtpConHelper();
    }

    public static AbstractFtp getInstance(String ftpType) {
        if (ftpType.equals(FILE_TRANS_FTP)) {
            return getFtpInstance();
        }else if (ftpType.equals(FILE_TRANS_SFTP)) {
            return getSFtpInstance();
        }
        return null;
    }
}
