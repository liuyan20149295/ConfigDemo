package com.liuyan.client;

import com.liuyan.zk.CuratorOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

public class ConfigClient {
    static Logger logger = LogManager.getLogger(ConfigClient.class);
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String monitorFile= "D:/test/jdbc.txt";
        String zkPath = "/config/jdbc";
        String fileName = "config.properties";
        ClassLoader classLoader = ConfigClient.class.getClassLoader();
          Thread thread= new Thread(new Runnable() {
              @Override
              public void run() {
                  CuratorOperator curatorOperator=new CuratorOperator(classLoader,fileName);
                  try {
                      curatorOperator.watcher(zkPath,monitorFile);
                  } catch (Exception e) {
                      logger.error("monitering the node is error", e);
                  }
              }
          }) ;
          thread.start();
          countDownLatch.await();

    }
}
