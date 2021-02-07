package com.luo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ShardReentrantLockComponent {

    @Autowired
    private CuratorFramework curatorFramework;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardReentrantLockComponent.class);

    /**
     * 该方法为模板方法，获得锁后回调 BaseLockHandler 中的 handler 方法
     * @return
     */
    public <T> boolean acquireLock(BaseLockHandler<T> baseLockHandler) {
        //获取要加锁的路径
        String path = baseLockHandler.getPath();
        //获取超时时间
        int timeOut = baseLockHandler.getTimeOut();
        //时间单位
        TimeUnit timeUnit = baseLockHandler.getTimeUnit();

        //通过 InterProcessMutex 该类来获取可重入共性锁
        InterProcessMutex lock = new InterProcessMutex(this.curatorFramework, path);
        //用于标识是否获取了锁
        boolean acquire = false;
        try {
            try {
                //成功获得锁后返回 true
                acquire = lock.acquire(timeOut, timeUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(acquire) {
                //获得锁后回调具体的业务逻辑
                return baseLockHandler.handler();
            } else {
                //没有获得锁返回 null
                return acquire;
            }
        } finally {
            try {
                if(acquire) {
                    //释放锁
                    lock.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 判断节点是否存在
     *
     * @param path 路径
     * @return true-存在  false-不存在
     */
    public boolean isExistNode(String path) {
        try {
            Stat stat = curatorFramework.checkExists().forPath(path);
            return stat != null ? true : false;
        } catch (Exception e) {
            LOGGER.error("===!判断zk节点是否存在!===",e);
            return false;
        }
    }

    /**
     * 创建节点
     *
     * @param path       路径
     * @param createMode 节点类型
     * @param data       节点数据
     * @return 是否创建成功
     */
    public boolean crateNode(String path, CreateMode createMode, String data) {
        try {
            curatorFramework.create().withMode(createMode).forPath(path, data.getBytes());
        } catch (Exception e) {
            LOGGER.error("===!创建zk节点!===",e);
            return false;
        }
        return true;
    }

    /**
     * 获取节点数据
     *
     * @param path 路径
     * @return 节点数据，如果出现异常，返回null
     */
    public String getNodeData(String path) {
        try {
            byte[] bytes = curatorFramework.getData().forPath(path);
            return new String(bytes);
        } catch (Exception e) {
            LOGGER.error("===!获取zk节点数据!===",e);
            return null;
        }
    }

    /**
     * 更新节点数据
     *
     * @param path     路径
     * @param newValue 新的值
     * @return 更新结果
     */
    public boolean updateNodeData(String path, String newValue) {
        //判断节点是否存在
        if (!isExistNode(path)) {
            return false;
        }
        try {
            curatorFramework.setData().forPath(path, newValue.getBytes());
        }
        catch (Exception e) {
            LOGGER.error("===!更新zk节点数据!===",e);
            return false;
        }
        return true;
    }
}
