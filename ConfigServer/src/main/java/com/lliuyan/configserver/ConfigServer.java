package com.lliuyan.configserver;

public class ConfigServer {
    public static void main(String[] args) {
        ClassLoader classLoader = ConfigServer.class.getClassLoader();
        String configFile = "config.properties";
        String zkPath = "/config/jdbc";
        String jdbcPath = "D:/test";
        LocalFileListenerAdaptor localListenerAdaptor = new LocalFileListenerAdaptor(classLoader, configFile, zkPath);
        LocalFileAlterationMonitor monitor = new LocalFileAlterationMonitor(jdbcPath, ".txt", localListenerAdaptor);
        monitor.start();
    }
}
