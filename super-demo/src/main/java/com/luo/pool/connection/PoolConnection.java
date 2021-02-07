package com.luo.pool.connection;

import java.sql.Connection;

public class PoolConnection<T> {
    private Connection connection;
    private boolean isBusy;

    public PoolConnection(Connection connection, boolean isBusy) {
        this.connection = connection;
        this.isBusy = isBusy;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

}
