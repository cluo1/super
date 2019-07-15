package com.luo.pool;

import com.luo.pool.connection.PoolConnection;

public interface IPool {
    PoolConnection getPoolConnection();
    void createPool(int size);
}
