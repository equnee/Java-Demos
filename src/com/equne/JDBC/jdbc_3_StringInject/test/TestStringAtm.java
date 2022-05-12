/* abstract: 测试SQL简单的字符串注入：1' or '1' = '1
   date: 2022-05-11
 */
package com.equne.JDBC.jdbc_3_StringInject.test;

import java.sql.*;

public class TestStringAtm {

    /* login：一个简单的登录方法
        当在密码中输入：xxx' or '1' = '1 ，即使用SQL注入时，任意用户名和密码都可以登录成功
     */


    public String login(String aname, String apassword){
        String result = "用户名或密码错误";
        String className = "com.mysql.cj.jdbc.Driver"; // 8.x版本
        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "rootroot";
        // 问题1：允许字符串拼接
        // String sql = "SELECT * FROM atm WHERE aname = '" + aname + "' AND apassword = '" + apassword + "'";
        String sql = "SELECT * FROM atm WHERE aname = ? AND apassword = ?";

        try {
            Class.forName(className);
            Connection conn = DriverManager.getConnection(url, user, password);
            // Statement stat = conn.createStatement();
            // ResultSet rs = stat.executeQuery(sql);
            PreparedStatement pstat = conn.prepareStatement(sql);
            // 给问号赋值
            pstat.setString(1, aname);
            pstat.setString(2, apassword);
            ResultSet rs = pstat.executeQuery();
            if(rs.next()){
                // 问题2：判断不严谨
                return "登录成功";
            }
            conn.close();
            pstat.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
