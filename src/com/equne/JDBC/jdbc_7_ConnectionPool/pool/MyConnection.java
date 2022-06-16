package com.equne.JDBC.jdbc_7_ConnectionPool.pool;

import com.sun.xml.internal.ws.api.server.Adapter;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 *  摘要：描述一个真实连接和一个状态的关系
 *    —— 将一个真实可用连接和一个状态封装在一起，形成一个新的对象MyConnection
 */
public class MyConnection extends AdapterConnection {

    private static String className; // 配置文件 properties
    private static String url;
    private static String user;
    private static String password;


    // 连接
    private Connection conn;
    // 状态（false：连接空闲——可用 / true：连接被占用——不可用）
    private boolean used = false;


    // 一个静态块：为了加载类时只执行一次
    static {
        try {
            // 赋值
            className = ConfigReader.getPropertyValue("className");
            url = ConfigReader.getPropertyValue("url");
            user = ConfigReader.getPropertyValue("user");
            password = ConfigReader.getPropertyValue("password");

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




    // =================================================================================================================
    // (添加适配器，过滤剩余的空方法 ——> 添加适配器 Adapter（类适配器、对象适配器、->缺省适配器）
    // 以下三个方法为适配器中重写的三个方法。至此，用户的使用与原来用法一样。



    @Override
    public Statement createStatement() throws SQLException {
        return this.conn.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        // 需要创建一个 PreparedStatement 对象
        // Connection conn = DriverManager.getConnection(); // 也是多态的效果，相当于 Connection 类型指向 JDBC4Connection 类对象
        // 1.copy别人的 ——> "别人"：Connection的某一个真实子类，接口里的是抽象方法
        //     原本不知道那个类是谁，现在知道了：JDBC4Connection，是通过导包得到的，导入的包中只有.class文件，无法粘贴。
        // 2.继承你
        //     原本 JDBC4Connection 没有默认修饰符，无法继承：只有在当前包内才允许继承。
        // 3.自己写
        //     不会，技术垄断，只有原来那个真实的Connection才会做这件事。通过DriverManager点出来的。
        PreparedStatement pstat = this.conn.prepareStatement(sql);
        // 使用当前类属性的方法，当作自己的东西返回（静态代理模式：我知道我代理的人是谁）方法没变，但内部变了。
        // 静态代理模式，代理类中都有一个真实对象作为属性，我知道代理的是谁。
        // 动态代理模式：代理是一个方法，没有对象。
        return pstat;
    }


    @Override
    public void close() throws SQLException {
        // 此处重写：释放连接状态，不是真的直接关闭
        this.used = false;
    }


}
