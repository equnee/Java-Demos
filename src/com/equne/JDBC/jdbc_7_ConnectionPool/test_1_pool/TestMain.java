package com.equne.JDBC.jdbc_7_ConnectionPool.test_1_pool;

import com.equne.JDBC.jdbc_7_ConnectionPool.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestMain {

    public static void main(String[] args) throws Exception{

        /*  表面上没有变，实际上底层机制变了：
         1 创建时间快
         2 无线程安全问题
         3 未连接排队等待机制
         4 连接参数信息可修改 */

        /*
            至此使用了至少三种设计模式：单例模式 / 静态代理模式 / 缺省适配器模式
               另外，读取文件：缓存机制，配置文件 / 线程安全问题等待问题
         */

        // 以下DAO层方法一样，代码冗余问题待解决，今后封装到只剩一条SQL语句，让所有JDBC流程都发生变化，让动态代理干活。
        //         （我们写DAO，DAO加上注解写SQL语句，动态代理让它读取DAO上的语句，找到真实类去创建JDBC：底层从连接池中获取连接。框架：封装连接池和ORM框架——> jar包。今后只需要写一个方法，定义一个注解，写一个SQL就可以干活了。）

        // 利用连接池的JDBC六部曲 几乎没有变化
        // 1. 导包
        // 2. 加载连接池对象（⚠️：需要配置一个文件，写连接的参数信息） <—— 只有此处不同
        ConnectionPool pool = ConnectionPool.getInstance();
        // 3. 获取连接
        Connection conn = pool.getConnection();
        // 4. 状态参数
        PreparedStatement pstat = conn.prepareStatement("");
        // 5. 执行操作
        ResultSet rs = pstat.executeQuery();
        // 6. 关闭资源
        rs.close();
        pstat.close();
        conn.close();




        // ————————————————————————————————————————
        // 模拟一个测试
        TestThread tt1 = new TestThread();
        TestThread tt2 = new TestThread();
        TestThread tt3 = new TestThread();
        TestThread tt4 = new TestThread();
        TestThread tt5 = new TestThread();
        TestThread tt6 = new TestThread();
        tt1.start();
        tt2.start();
        tt3.start();
        tt4.start();
        tt5.start();
        tt6.start();



        // ————————————————————————————————————————
        // 最初的使用方法：

//        // 1. 导包
//        // 2. 加载连接池对象
//        long t1 = System.currentTimeMillis();
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        // 3. 获取连接
//        MyConnection mc = connectionPool.getMC();
//        Connection conn = mc.getConn();
//        long t2 = System.currentTimeMillis();
//        // 4. 状态参数
//        PreparedStatement pstat = conn.prepareStatement("SELECT * FROM ATM");
//        ResultSet rs = pstat.executeQuery();
//        // 5. 正常使用rs做业务
//        while(rs.next()){
//            System.out.println(rs.getString("aname"));
//        }
//        // 6. 关闭资源
//        rs.close();
//        pstat.close();
//        mc.setUsed(false); // 修改连接状态：是释放，不是真的关闭。
//
//        long t3 = System.currentTimeMillis();
//        System.out.println(t2-t1); // 1245
//        System.out.println(t3-t2); // 29

    }
}
