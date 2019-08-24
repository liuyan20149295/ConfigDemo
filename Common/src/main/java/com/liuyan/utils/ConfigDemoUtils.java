package com.liuyan.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConfigDemoUtils {
    static Logger logger = LogManager.getLogger(ConfigDemoUtils.class);

    public static LinkedHashMap<String, String> read2Object(String values) throws Exception {
        String[] keySet ;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (!values.isEmpty()) {
            keySet = values.split(",");
            for (String mapValue : keySet) {
                String[] keyValue = mapValue.split("-");
                map.put(keyValue[0], keyValue[1]);
            }
        } else {
            throw new Exception("the values is empty");
        }
        return map;
    }

    public static String read2String(LinkedHashMap<String, String> maps)  {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> values : maps.entrySet()) {
            stringBuffer.append(values.getKey());
            stringBuffer.append("-");
            stringBuffer.append(values.getValue());
            stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }

    public static LinkedHashMap<String, String> getMonitorValue(File file) {
        LinkedHashMap<String, String> properties = new LinkedHashMap<>();
        InputStreamReader reader = null;
        try {
            // 建立一个输入流对象reader
            reader = new InputStreamReader(new FileInputStream(file));
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null ) {
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

    public static void writeMonitorV2File(LinkedHashMap<String, String> values, String fileName) {
        try {
            String line = System.getProperty("line.separator");
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter  bw=new BufferedWriter(fw);
            Set set = values.entrySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                StringBuffer str = new StringBuffer();
                Map.Entry entry = (Map.Entry) iter.next();
                str.append(entry.getKey() + "=" + entry.getValue()).append(line);
                bw.write(str.toString());
            }
            bw.close();
            fw.close();
            logger.info("write the file success");
        } catch (IOException e) {
            logger.error("write the file is error", e);
        }
    }


}
