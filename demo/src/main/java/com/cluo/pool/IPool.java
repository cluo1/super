package com.cluo.pool;

import com.cluo.pool.connection.PoolConnection;

public interface IPool {
    PoolConnection getPoolConnection();
    void createPool(int size);
}
