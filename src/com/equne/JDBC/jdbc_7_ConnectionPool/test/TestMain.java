package com.equne.JDBC.jdbc_7_ConnectionPool.test;

import com.equne.JDBC.jdbc_7_ConnectionPool.pool.ConnectionPool;
import com.equne.JDBC.jdbc_7_ConnectionPool.pool.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestMain {

    public static void main(String[] args) throws Exception{

        // 1. 导包
        // 2. 加载连接池对象
        long t1 = System.currentTimeMillis();
        ConnectionPool connectionPool = new ConnectionPool();
        // 3. 获取连接
        MyConnection mc = connectionPool.getMC();
        Connection conn = mc.getConn();
        long t2 = System.currentTimeMillis();
        // 4. 状态参数
        PreparedStatement pstat = conn.prepareStatement("SELECT * FROM ATM");
        ResultSet rs = pstat.executeQuery();
        // 5. 正常使用rs做业务
        while(rs.next()){
            System.out.println(rs.getString("aname"));
        }
        // 6. 关闭资源
        rs.close();
        pstat.close();
        mc.setUsed(false); // 修改连接状态：是释放，不是真的关闭。

        long t3 = System.currentTimeMillis();
        System.out.println(t2-t1); // 1245
        System.out.println(t3-t2); // 29

    }
}
