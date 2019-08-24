package com.liuyan.configserver;

import com.liuyan.utils.ConfigDemoUtils;
import com.liuyan.zk.CuratorOperator;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalFileListenerAdaptor extends FileAlterationListenerAdaptor {
    static Logger logger = LogManager.getLogger(LocalFileListenerAdaptor.class);
    private ClassLoader classLoader = LocalFileListenerAdaptor.class.getClassLoader();
    private String fileName = "config.properties";
    private String zkPath = "/config/jdbc";
    CuratorOperator curatorOperator = new CuratorOperator(classLoader, fileName);

    public LocalFileListenerAdaptor(ClassLoader classLoader, String fileName, String zkPath) {
        this.classLoader = classLoader;
        this.fileName = fileName;
        this.zkPath = zkPath;
    }

    @Override
    public void onFileCreate(File file) {
        logger.info("the files created");
        LinkedHashMap<String, String> monitorV = new LinkedHashMap<>();
        monitorV = ConfigDemoUtils.getMonitorValue(file);
        String zkValue = null;
        zkValue = ConfigDemoUtils.read2String(monitorV);
        curatorOperator.createNode(zkPath, zkValue.getBytes(Charset.defaultCharset()));

    }

    @Override
    public void onFileChange(File file) {
        logger.info("the files changed");
        LinkedHashMap<String, String> monitorV = new LinkedHashMap<>();
        monitorV = ConfigDemoUtils.getMonitorValue(file);
        String zkValue = null;
        zkValue = ConfigDemoUtils.read2String(monitorV);
        curatorOperator.update(zkPath, zkValue.getBytes(Charset.defaultCharset()));
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("the files deleted");
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        logger.info("start");
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        logger.info("stop");
        super.onStop(observer);
    }

    @Override
    public void onDirectoryChange(File directory) {
        logger.info("the dir changed");
        super.onDirectoryChange(directory);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        logger.info("the dir created");
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        logger.info("the dir deleted");
        super.onDirectoryCreate(directory);
    }

}