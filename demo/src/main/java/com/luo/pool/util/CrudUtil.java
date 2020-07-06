package com.luo.pool.util;

import com.luo.pool.IPool;
import com.luo.pool.connection.PoolConnection;
import com.luo.pool.factory.DefaultPoolFactory;
import com.luo.pool.factory.PoolFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudUtil {
    private static IPool dfPool = null;
    static {
        PoolFactory factory = new DefaultPoolFactory();
       dfPool = factory.createDfPool();
    }

    public static List<Object> query(String sql,Object...params){

        PoolConnection poolConnection = dfPool.getPoolConnection();
        Connection connection = poolConnection.getConnection();

        List<Object> list = new ArrayList<Object>();
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(params != null && params.length > 0){
                for (int i=0;i<params.length;i++){
                    preparedStatement.setObject(i+1,params[i]);
                }
            }
           resultSet = preparedStatement.executeQuery();

            ResultSetMetaData md = resultSet.getMetaData();
            while (resultSet.next()){
                for (int i=1;i<=md.getColumnCount();i++){
                    Map<Object, Object> dataMap = new HashMap<Object, Object>();
                    dataMap.put(md.getColumnName(i),resultSet.getObject(i));
                    list.add(dataMap);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            poolConnection.setBusy(false);
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }



}
