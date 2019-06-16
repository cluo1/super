package com.cluo.pool;

import com.cluo.pool.connection.PoolConnection;
import com.cluo.pool.util.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultPool implements IPool {

    private List<PoolConnection> PoolConnects = new ArrayList<PoolConnection>();

    private int setp;

    private int maxSize;

    @Override
    public PoolConnection getPoolConnection() {

        if (PoolConnects.size() < 1) {
            throw new RuntimeException("连接池初始化失败");
        }
        PoolConnection poolConnection = getPoolFromConnection();
        while(poolConnection == null){
            createPool(setp);
            poolConnection = getPoolFromConnection();
        }

        return poolConnection;
    }

    @Override
    public void createPool(int size) {
        setp = DBUtil.step;
        maxSize = DBUtil.maxSize;

        synchronized (this) {
            if (PoolConnects.size() > maxSize || PoolConnects.size() + size > maxSize) {
                throw new RuntimeException("连接池已满");
            } else {
                while (size > 0) {
                    try {
                        PoolConnects.add(new PoolConnection(DBUtil.getConnect(), false));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    size--;
                }
            }
        }
    }

    private synchronized PoolConnection getPoolFromConnection() {
        for (PoolConnection pc : PoolConnects) {
            if (!pc.isBusy()) {
                pc.setBusy(true);
                return pc;
            }else {
                throw new RuntimeException("暂无空闲连接");
            }
        }
        return null;
    }
}
