package com.lliuyan.configserver;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LocalFileListenerAdaptor extends FileAlterationListenerAdaptor{
    static Logger logger = LogManager.getLogger(LocalFileListenerAdaptor.class);
    @Override
    public void onFileChange(File file) {
        logger.info("the files changed");
        super.onFileChange(file);
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