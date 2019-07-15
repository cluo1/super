package com.luo.pool.factory;

import com.luo.pool.IPool;
import com.luo.pool.util.DBUtil;
import com.luo.utils.ReflectUtil;
import org.springframework.stereotype.Component;

@Component
public class DefaultPoolFactory implements PoolFactory{

    @Override
    public IPool createDfPool() {
        return SinglePool.getInstance("DefaultPool");
    }

}
class SinglePool {
    private static IPool ipool;

    public static IPool getInstance(String className){
        if (ipool == null){
            synchronized (SinglePool.class){
                try {
                    ipool = (IPool) ReflectUtil.getObject(className);
                    ipool.createPool(DBUtil.initSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ipool;
    }

}