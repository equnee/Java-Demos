package com.equne.JDBC.jdbc_atmProject.Dao;

import com.equne.JDBC.jdbc_atmProject.domain.Atm;

import java.sql.*;

public class AtmDao {
    /*
     *    以下方法为纯粹的JDBC读写数据库操作，没有任何逻辑
     *
     *     * 对于atm表格中，单条记录的：新增，修改，删除，查询
     *       - 新增、修改、删除 ——> 代码几乎一致（只差一条sql语句）——> 优化
     *       - 查询 ——> 与别的类查询几乎一致（参数不同）
     *
     *     * 框架：Mybatis / Hibernate持久层 —— 专门负责读写数据库（DAO）
     **/

    /* delete: 负责将某行记录从数据库删除
     */
    public void delete(String aname){
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "rootroot";
        Connection conn = null;
        Statement stat = null;
        String sql = "DELETE FROM atm WHERE aname ='" + aname +"'";
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            stat.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn!=null){
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
        }
    }

    /* insert: 负责将新一行记录写入数据库
     */
    public void insert(Atm atm){
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "rootroot";
        String sql = "INSERT INTO atm VALUES('" + atm.getAname() +"','" + atm.getApassword() + "'," + atm.getAbalance() +")";
        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            stat.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn!=null){
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
        }
    }


    /* update：连接数据库、做数据库的写操作
        封装：存款/取款/修改密码操作代码，通用的update操作代码
        参数：Atm / 返回值：无 (int更新行数)
     */
    public void update(Atm atm){
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "rootroot";
        Connection conn = null;
        Statement stat = null;
        String sql = "UPDATE atm SET apassword = '" + atm.getApassword() + "', abalance = '" + atm.getAbalance() + "' WHERE aname ='" + atm.getAname() + "'";
        try {
            Class.forName(className);
            conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
            stat.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流：放在finally内必需执行。
            // 分成三个try/catch分开关闭，增强健壮性：当某个流出现异常无法关闭时，其他流也能正常关闭。
            try {
                if (conn!=null){ // 若为空时关闭会抛出空指针异，因此限定当流创建成功即非空时才关闭。
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
        }
    }

    /* selectOne：查询一条记录
        封装：登录和查询的重复代码
        参数：aname / 返回值：Object (domain) （返回值不能是ResultSet，因为程序最后会使用rs.close()将结果集关闭。可以将结果集中的数据取出来，将数据存在一个容器里返回。
     */
    public Atm selectOne(String aname) {
        Atm atm = null;
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/atm";
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
                if (conn!=null){ // 若为空时关闭会抛出空指针异，因此限定当流创建成功即非空时才关闭。
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
}
