package com.liuyan.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.LinkedHashMap;

public class ConfigDemoUtils {
    static Logger logger = LogManager.getLogger(ConfigDemoUtils.class);
    public static Object read2Object(byte[] values) throws IOException {
        Object rerurnV=null;
        ByteArrayInputStream bin = new ByteArrayInputStream(values);
        ObjectInputStream in_ = null;
        try {
            in_ = new ObjectInputStream(bin);
            rerurnV=in_.readObject();
        } catch (IOException e) {
            logger.error("read the values is error",e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            in_.close();
            bin.close();
        }
        return rerurnV;
    }
    public static byte[] read2Byte(Object values) throws IOException {
        byte[] returnV=null;
        ByteArrayOutputStream bou = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bou);
            out.writeObject(values);
            out.flush();
            returnV = bou.toByteArray();
        } catch (IOException e) {
            logger.error("read the values is error",e);
        } finally {
            out.close();
            bou.close();
        }
        return returnV;
    }

    public static LinkedHashMap<String,String> getMonitorValue(File file){
        LinkedHashMap<String, String> properties = new LinkedHashMap<>();
        InputStreamReader reader = null;
        try {
            // 建立一个输入流对象reader
            reader = new InputStreamReader(new FileInputStream(file));
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                String[] parts = line.split("=");
                properties.put(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            logger.error("the file is not found", e);
        } catch (IOException e) {
            logger.error("read the file is error", e);
        }

        return properties;
    }

}
