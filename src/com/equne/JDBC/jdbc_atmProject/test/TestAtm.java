/* abstract: 使用jdbc、mvc思想的银行atm小项目
 * date: 2022-05-09  */
package com.equne.JDBC.jdbc_atmProject.test;

import com.equne.JDBC.jdbc_atmProject.domain.Atm;

import java.sql.*;

public class TestAtm {

    // update：连接数据库、做数据库的写操作
    // 封装存款/取款/修改密码的内部代码（代码冗余）、转账、修改密码
    // 参数：Atm / 返回值：无 (int更新行数)
    public void update(Atm atm){
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "root";

        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // selectOne：查询一条记录
    // 封装登录和查询的重复代码
    // 参数：aname / 返回值：Object (domain) （返回值不能是ResultSet，因为程序最后会使用rs.close()将结果集关闭。可以将结果集中的数据取出来，将数据存在一个容器里返回。
    public Atm selectOne(String aname) {
        Atm atm = null;
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test_atm_20220509";
        String user = "root";
        String password = "rootroot";
        String sql = "SELECT aname, apassword, abalance FROM atm WHERE aname = '" + aname + "'";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                atm = new Atm(); // 懒加载机制
                atm.setAname(rs.getString("aname"));
                atm.setApassword(rs.getString("apassword"));
                atm.setAbalance(rs.getFloat("abalance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流：放在finally内必需执行。
            // 分成三个try/catch分开关闭，增强健壮性：当某个流出现异常无法关闭时，其他流也能正常关闭。
            try {
                if (conn!=null){ // 若为空即创建失败，则无需关闭，会直接抛出异常。因此限定当流创建成功即非空时才关闭。
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(stat!=null){
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(rs!=null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return atm;
    }



    // login: 登录
    public String login(String aname, String apassword){
        Atm atm = this.selectOne(aname);
        if(atm!=null && atm.getApassword().equals(apassword)){
            return "登录成功";
        }
        return "用户名或密码错误";
    }

    // check: 查询余额
    public float check(String aname){
        // this.的写法省略了'Atm atm ='的过程。
        return this.selectOne(aname).getAbalance();
    }

    // deposit: 存款
    // updade，修改数据库内的信息
    public void deposit(String aname, float depositAmount){

    }

    // withdraw：取款
    // updade，修改数据库内的信息
    public void withdraw(String aname, float withdrawalAmout){

    }

    // 转账

    // 开户

    // 销户

}
