package com.luo.service;



public interface ISqlLoaderService {
    public String getCtlTemplet(String filePath,String dataFileName,String tableName,String split,String filedName) ;

    public void ctlFileWriter(String strctl,String filePath,String ctlfileName) ;
    public  String sqlloder(String userName, String passWord,
                          String dataBase, String filePath, String ctlFileName,String dataFileName);

}
