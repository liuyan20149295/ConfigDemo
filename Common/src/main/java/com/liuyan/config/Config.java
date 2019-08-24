package com.liuyan.config;

import com.liuyan.utils.ConfigDemoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Config {
    static Logger logger = LogManager.getLogger(Config.class);
    private ClassLoader classLoader = null;
    private String fileName = null;

    public Config(ClassLoader classLoader, String fileName) {
        this.classLoader = classLoader;
        this.fileName = fileName;
    }


    public Properties getProperties() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = classLoader.getResourceAsStream(fileName);
            prop.load(in);     ///加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                System.out.println(key + ":" + prop.getProperty(key));
            }
        } catch (Exception e) {
            logger.error("read the properties is error",e);
        } finally {
            in.close();
        }
        return prop;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

