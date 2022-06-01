package com.equne.JDBC.jdbc_7_ConnectionPool.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *  本类目的：启动程序时一次性读取properties配置文件的信息，存在map缓存里
 *      为了创建连接和连接池管理而使用
 */
public class ConfigReader {

    private static Properties properties; // 用于读取的流对象：确切地说是个map集合，可以读取文件的集合，然后遍历
    private static Map<String,String> configMap; // 存放文件中的 key=value

    // 该静态块为了将map集合填满
    static {
        properties = new Properties();
        configMap = new HashMap<>();
        // 读取配置文件:获取输入流
        try {
            /* 🌊 InputStream写法：按照原来new写法，需要写相对路径：new FileReader("src/configuration.properties);
                 - 现在执行的项目时一个 JavaProject，不需要服务器支持，只需要调用main方法
                 - 未来执行的项目是一个 WebProject，需要的是Tomcat这种Web容器的支持
                    * 未来会把所有Java类当作请求的资源统一交给Web容器来进行管理
                    * 通过浏览器向web容器发送请求，请求容器帮我们找到资源，执行资源，最终给予我们响应
                    * 此时路径不是src，使用绝对路径将找不到资源。
                        * path：配置classPath ——> 告知给类加载器ClassLoader，类.class文件在哪
             */
            // 通用写法：使用当前线程，产生当前执行时的类加载器，加载文件。
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties");
            properties.load(inputStream);
            // properties文件结构是一个map集合结构。将文件中所有内容(map集合)都读取出来进行遍历。

            // 获取全部的key
            Enumeration en = properties.propertyNames(); // 可理解为 Set = map.keySet();
            // 遍历
            while(en.hasMoreElements()){ // set--Iterator迭代器 相当于 it.hasNext()
                String key = (String)en.nextElement();
                String value = properties.getProperty(key);
                configMap.put(key,value);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 给使用者提供一个获取value的方法
    public static String getPropertyValue(String key){
        return configMap.get(key);
    }

}
