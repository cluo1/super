//package com.luo.zk;
//
//import com.luo.BaseLockHandler;
//import com.luo.DemoApplication;
//import com.luo.ShardReentrantLockComponent;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = DemoApplication.class)
//public class zkTest {
//
//    @Autowired
//    private ShardReentrantLockComponent lockComponent;
//
//    @Test
//    public void test(){
//        String path="/super";
//        boolean existNode = lockComponent.isExistNode(path);
//        String nodeData = lockComponent.getNodeData(path);
//
//        //分布式锁
//        lockComponent.acquireLock(new BaseLockHandler<Object>(path) {
//            @Override
//            public boolean handler() {
//                //业务处理
//                return false;
//            }
//        });
//    }
//}
