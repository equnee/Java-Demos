package com.equne.JDBC.jdbc_7_ConnectionPool.test;

import com.equne.JDBC.jdbc_7_ConnectionPool.pool.ConnectionPool;
import com.equne.JDBC.jdbc_7_ConnectionPool.pool.MyConnection;

import java.sql.Connection;

/**
 *  模拟多线程并发访问连接池的小测试
 */
public class TestThread extends Thread{

    public void run(){
        // 获取一个连接，把持一会儿不释放
        ConnectionPool pool = ConnectionPool.getInstance();
        MyConnection mc = pool.getMC();
        Connection conn = mc.getConn();
        // conn 是用户获取到的连接 可以拿去使用
        System.out.println(conn);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // mc.setUsed(false); // 释放
    }

}
