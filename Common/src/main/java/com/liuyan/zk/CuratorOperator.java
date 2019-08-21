package com.liuyan.zk;

import com.liuyan.config.Config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class CuratorOperator {
    static Logger logger = LogManager.getLogger(CuratorOperator.class);
    public CuratorFramework client = null;
    public static String zkServerPath = null;
    private  ClassLoader  classLoader =null;
    private  String  fileName =null;

    public CuratorOperator() {
        Config config = new Config(classLoader, fileName);
        try {
            zkServerPath = config.getProperties().getProperty("zkAddress");
        } catch (IOException e) {
            logger.error("read the properties is error", e);
        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        client = CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                //namespace：
                .build();
        client.start();
    }

    public CuratorOperator(ClassLoader classLoader, String fileName) {
        this.classLoader = classLoader;
        this.fileName = fileName;
    }

    public  void createNode(String path, byte[] value) {
        try {
            client.create()
                    .creatingParentsIfNeeded()  //如果父节点不存在，则自动创建
                    .withMode(CreateMode.EPHEMERAL)  //节点模式，临时
                    .inBackground()//后台方式，即异步方式创建
                    .forPath(path, value);

        } catch (Exception e) {
            logger.error("add the node is error", e);
        }
    }

    public  void update(String path, byte[] value) {
        try {
            //先获取节点状态信息
            Stat stat = new Stat();
            //获取节点值，并同时获取节点状态信息
            byte[] data = client.getData().storingStatIn(stat).forPath(path);
            //更新节点
            client.setData()
                    .withVersion(stat.getVersion())  //版本校验，与当前版本不一致则更新失败,默认值-1无视版本信息进行更新
                    //  .inBackground(paramBackgroundCallback)  //异步修改数据，并进行回调通知
                    .forPath(path, value);
        } catch (Exception e) {
            logger.error("update the node is error", e);
        }
    }
}
