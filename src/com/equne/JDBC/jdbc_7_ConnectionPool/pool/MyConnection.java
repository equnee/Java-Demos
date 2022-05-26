package com.equne.JDBC.jdbc_7_ConnectionPool.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  摘要：描述一个真实连接和一个状态的关系
 *    —— 将一个真实可用连接和一个状态封装在一起，形成一个新的对象MyConnection
 */
public class MyConnection {

    private static String className = "com.mysql.cj.jdbc.Driver"; // 配置文件 properties
    private static String url = "jdbc:mysql://localhost:3306/atm";
    private static String user = "equne";
    private static String password = "1234";


    // 连接
    private Connection conn;
    // 状态（false：连接空闲——可用 / true：连接被占用——不可用）
    private boolean used = false;


    // 一个静态块：为了加载类时只执行一次
    {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 一个普通块：为了给属性赋值
    {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 方法：属性对应的get/set方法
    public Connection getConn() {
        return conn;
    } // 连接只获取，不需要存，因为连接在静态块中已经设置。
    public boolean isUsed() { // boolean的get方法：is开头（规约）
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
}
