/* abstract: JDBC连接数据库：写操作
 *  date: 2022-5-7
 *  mysql: 8.0.29  */
package com.equne.JDBC.jdbc_1_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {

    public static void main(String[] args) {

        try {
            // 1. 导包（工程目录下 /lib/mysql-connector-java-8.0.29.jar
            // 2. 加载驱动类（com.mysql.cj.jdbc.Driver）
            String className = "com.mysql.cj.jdbc.Driver";
            Class.forName(className); // // 加载类，Driver类中的静态元素就执行了，不需要(Class clazz =..)来接收返回值。
            // 3. 获取连接
            //  jdbc:mysql://ip:port/database名
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "root";
            Connection conn = DriverManager.getConnection(url, user, password);
            // 4. 创建状态参数（流:读/写）（返回值：count—行数）
            Statement stat = conn.createStatement();
            // 5. 执行操作
            //    写(DDL)：INSERT/DELETE/UPDATE（做了数据库更新） — stat.executeUpdate(String sql);
            //    读(DQL)：SELECT（没有做数据库更新，但需要处理结果）— stat.executeQuery(String sql);
            String sql = "insert into emp(id, name, age) values(4, 'peach', 14)";
            int count = stat.executeUpdate(sql);
            System.out.println("Query OK, " + count + " row affected. :)");
            // 6. 关闭
            stat.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
