package com.luo;

import com.luo.config.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TreeSet;

/**
 * 分布式锁
 *
 * @author yichen11
 * @date 2020-11-27
 */
public class DistributedLock {

    private static final String ROOT_LOCK_NAME = "/locks";

    private static final String SEPARATOR = "/";

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    /**
     * 分布式组件在集群状态下，任务是否触发的锁
     *
     * 功能：同个组件部署多台时，只有一个服务能成功获取锁并执行任务，其他的都会失败
     *
     * 原理：基于zookeeper的临时有序节点，定时任务触发时，
     * 多个服务同时注册zk临时有序节点，序号最小的机器执行任务，其他的不执行
     *
     * @param zkAddress zookeeper的连接地址
     * @param rootName 锁的根路径名称
     * @param lockName 锁名称
     * @return 是否获取锁
     */
    public static boolean triggerLock(String zkAddress, String rootName, String lockName) {
        //拼装锁路径
        rootName = ROOT_LOCK_NAME.concat(SEPARATOR).concat(rootName);
        lockName = rootName.concat(SEPARATOR).concat(lockName);
        //获取zk客户端
        ZooKeeper zkCli = new ZkClient(zkAddress, ROOT_LOCK_NAME).getZkCli();
        if(zkCli == null) {
            LOGGER.info("ZkClient init fail![Zookeeper客户端初始化失败!]");
            return false;
        }
        try {
            //判断根目录节点是否存在，不存在则创建
            Stat rootStat = zkCli.exists(rootName, false);
            if(rootStat == null) {
                zkCli.create(rootName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建临时有序节点
            String curNodeName = zkCli.create(lockName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.info("LockNode [{}] created!", curNodeName);
            //获取所有的根目录下的节点
            List<String> children = zkCli.getChildren(rootName, false);
            TreeSet<String> nodeSet = new TreeSet<>();
            final String root = rootName;
            children.forEach(node -> nodeSet.add(root.concat(SEPARATOR).concat(node)));
            //获取最小的节点名
            String smallNode = nodeSet.first();
            //判断当前节点是不是最小节点，是表明获取到锁，否表示未获取到锁
            if(curNodeName.equals(smallNode)) {
                LOGGER.info("Current server get lock success!");
                return true;
            }
            //获取最小节点的信息
            Stat stat = zkCli.exists(smallNode, false);
            byte[] dataBytes = zkCli.getData(smallNode, false, stat);
            String nodeData = null;
            if(dataBytes != null) {
                nodeData = new String(dataBytes);
            }
            LOGGER.info("Another server [{}] get lock fail!", nodeData);
        } catch (Exception e) {
            LOGGER.error(" LockNode [lockName = {}] create fail![创建锁节点失败!]", lockName, e);
        }
        return false;
    }

}
