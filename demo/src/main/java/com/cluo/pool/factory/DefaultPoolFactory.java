package com.cluo.pool.factory;

import com.cluo.pool.IPool;
import com.cluo.pool.util.DBUtil;
import com.cluo.utils.ReflectUtil;

public class DefaultPoolFactory implements PoolFactory{

    @Override
    public IPool createDfPool() {
        return SinglePool.getInstance("com.cluo.pool.DefaultPool");
    }

}
class SinglePool {
    private static IPool ipool;

    public static IPool getInstance(String className){
        if (ipool == null){
            synchronized (SinglePool.class){
                try {
                    ipool = (IPool)ReflectUtil.getObject(className);
                    ipool.createPool(DBUtil.initSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ipool;
    }

}