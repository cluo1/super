package com.luo.zookeeper.znode;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZookeeperTest {
    private static ZooKeeper zookeeper;

    private String url = "192.168.117.129:2181,192.168.117.130:2181";

    public ZookeeperTest() throws IOException {
        zookeeper = new ZooKeeper(url, 5000, null);
    }

    public static void main(String[] args) throws Exception {
        try {
            ZookeeperTest zookeeperTest = new ZookeeperTest();
            System.out.println(zookeeperTest);

            List<String> children = zookeeper.getChildren("/", false);

            for (String s : children) {
                System.out.println("children:" + s);
            }
            byte[] data = zookeeper.getData("/superluo", false, null);
            System.out.println("data:" + new String(data));

            zookeeper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
        Stat stat = zookeeper.setData(path, data.getBytes(), -1);
        return stat;
    }
}
