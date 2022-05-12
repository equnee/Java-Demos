/* abstract: JDBC连接数据库：读操作（接收返回值）
 *  date: 2022-5-8
 *  mysql: 8.0.29  */
package com.equne.JDBC.jdbc_1_test;

import java.sql.*;

public class TestQuery {

    public static void main(String[] args) {

        try {
            String className = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "root";
            String sql = "select * from emp";

            // 1. 导包（工程目录下 /lib/mysql-connector-java-8.0.29.jar
            // 2. 加载驱动类（com.mysql.cj.jdbc.Driver）
            Class.forName(className);
            // 3. 获取连接
            //  jdbc:mysql://ip:port/database名
            Connection conn = DriverManager.getConnection(url, user, password);
            // 4. 创建状态参数（流:读/写）（返回值：count—行数）
            Statement stat = conn.createStatement();
            // 5. 执行操作
            //    写(DDL)：INSERT/DELETE/UPDATE（做了数据库更新） — stat.executeUpdate(String sql);
            //    读(DQL)：SELECT（没有做数据库更新，但需要处理结果）— stat.executeQuery(String sql);
            ResultSet rs = stat.executeQuery(sql);
            // if (rs.next()) {  — 单条数据，若确定只返回一条数据，尽量使用if，性能更高
            while (rs.next()) { // while: 多条数据
                // int id = rs.getInt("id");
                int id = rs.getInt(1); // 参数1：从第一行开始
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String job = rs.getString("job");
                Date addtime = rs.getDate("add_time");
                System.out.println(id + "--" + name + "--" + age + "--" + job + "--" + addtime );
            }
            System.out.println("query ok. :)");
            // 6. 关闭
            rs.close();
            stat.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
