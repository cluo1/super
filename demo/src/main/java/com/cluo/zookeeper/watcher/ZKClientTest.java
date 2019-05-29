package com.cluo.zookeeper.watcher;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

import java.util.List;

public class ZKClientTest<T> {
    private ZkClient zkClient = null;
    private String url = "192.168.117.129:2181,192.168.117.130:2181,192.168.117.131:2181";

    public ZKClientTest() {
        zkClient = new ZkClient(url, 5000, 5000, new SerializableSerializer());
    }

    public void createPresistent(String path, String data) {
        zkClient.createPersistent(path, data);
    }

    public void delete(String path) {
        zkClient.deleteRecursive(path);
    }

    public void writeData(String path, String data) {
        zkClient.writeData(path, data);
    }

    public T readData(String path) {
        return zkClient.readData(path);
    }

    public boolean exits(String path){
        return zkClient.exists(path);
    }

    public void listen(String path) {
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("变更节点："+path+","+data);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.printf("删除节点：%s", path);
            }
        });
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String path, List<String> list) throws Exception {
                System.out.println("path:" + path + ",list:" + list);
            }
        });
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if (state == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("连接成功");
                } else {
                    System.out.println("其他状态");
                }
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("重建session");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
                System.out.println("连接错误：" + throwable.getMessage());
            }
        });
    }

    @Test
    public void test() throws InterruptedException {
        String path = "/super";
        listen(path);
        if (exits(path)){
            delete(path);
        }
        createPresistent(path,"1");
        writeData(path,"2");
//        System.out.println(readData(path));
        Thread.sleep(Integer.MAX_VALUE);

    }
}
