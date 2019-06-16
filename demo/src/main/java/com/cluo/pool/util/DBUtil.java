package com.cluo.pool.util;

import com.cluo.pool.conf.DBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@DBConfig(ip = "127.0.0.1",database = "test",user = "root",pwd = "root")
public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    public static int initSize;
    public static int maxSize;
    public static int step;

    private static DBConfig annotation = DBUtil.class.getAnnotation(DBConfig.class);
    private static String ip;
    private static String database;
    private static String user ;
    private static String pwd ;
    private static int port ;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            initSize = annotation.initSize();
            step = annotation.step();
            maxSize = annotation.maxSize();
            database = annotation.database();
            ip = annotation.ip();
            port = annotation.port();
            user = annotation.user();
            pwd = annotation.pwd();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnect() throws SQLException {

        //jdbc:mysql://127.0.0.1:3306/test
        String url = String.format("jdbc:mysql://%s:%d/%s", ip, port, database);
        return DriverManager.getConnection(url,user,pwd);
    }

    public static void close(Connection connection){
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connect = null;
        try {
            connect = getConnect();
            logger.info("con:{}",connect);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connect);
        }
    }
}
