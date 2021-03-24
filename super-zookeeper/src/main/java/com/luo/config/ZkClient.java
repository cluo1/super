package com.luo.config;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建zookeeper连接
 *
 * @author yichen11
 * @date 2020-11-27
 */
public class ZkClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClient.class);

    //同步等待zkClient连接到服务器
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    //客户端session过期时长
    private static final int SESSION_TIMEOUT = 100000;

    //客户端
    private ZooKeeper zkCli;

    /**
     * 创建zookeeper客户端连接
     * @param zkAddress
     * @param rootName
     */
    public ZkClient(String zkAddress, String rootName) {
        try {
            //初始化zk客户端
            zkCli = new ZooKeeper(zkAddress, SESSION_TIMEOUT, watchedEvent -> {
                if(watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            });
            //阻塞线程等待唤醒
            connectedSignal.await();
            //判断根目录节点是否存在，不存在则创建
            Stat stat = zkCli.exists(rootName, false);
            if(stat == null) {
                zkCli.create(rootName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            LOGGER.error("Zookeeper not connected! [Zookeeper客户端连接失败!]", e);
        } catch (Exception e) {
            LOGGER.error("RootNode create exception! [根节点创建异常!]", e);
        }
    }

    public ZooKeeper getZkCli() {
        return zkCli;
    }
}
