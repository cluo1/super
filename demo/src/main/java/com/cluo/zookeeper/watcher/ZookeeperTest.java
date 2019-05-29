package com.cluo.zookeeper.watcher;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest implements Watcher {
    private static ZooKeeper zookeeper;

    private String url = "192.168.117.129:2181,192.168.117.130:2181,192.168.117.131:2181";
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
        Event.EventType type = event.getType();
        Event.KeeperState state = event.getState();
        String path = event.getPath();
        try {
            if (this.isExits("/super",true) != null) {
                System.out.println("data:"+this.getData("/super",true));
                System.out.println("type:"+type+"，state:"+state+"，path:"+path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ZookeeperTest() throws IOException, InterruptedException {
        this.zookeeper = new ZooKeeper(url, 5000, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
    }

    public void creatNode(String path, byte[] data) throws KeeperException, InterruptedException {
        zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zookeeper.getChildren(path, this);
        return children;
    }

    public String getData(String path, Boolean watcher) throws KeeperException, InterruptedException {
        byte[] data = zookeeper.getData(path, watcher, null);
        return new String(data);
    }

    public void setData(String path, String data) throws KeeperException, InterruptedException {
        zookeeper.setData(path, data.getBytes(), -1);
    }

    public void deleteNode(String path) throws InterruptedException, KeeperException {
        zookeeper.delete(path, -1);
    }

    public String getCTime(String path) throws KeeperException, InterruptedException {
        Stat stat = zookeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    public Stat isExits(String path,boolean watcher) throws KeeperException, InterruptedException {
        return zookeeper.exists(path, watcher);
    }

   /* public void closeConnection() throws InterruptedException {
        if (zookeeper != null) {
            zookeeper.close();
        }
    }*/


    public static void main(String[] args) {
        try {
            ZookeeperTest zk = new ZookeeperTest();
//            if (zk.isExits("/super",true) != null) {
//                zk.deleteNode("/super");
//            }
//            zk.creatNode("/super", "1".getBytes());
            zk.setData("/super","super");
//            String data = zk.getData("/super",true);
//            System.out.println("data:"+data);

            Thread.sleep(Long.MAX_VALUE);
         /*   List<String> children = zk.getChildren("/");
            for (String c : children) {
                System.out.println(c);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            try {
                zk.closeConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
