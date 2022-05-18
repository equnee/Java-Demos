/*
    持久层：读写数据库（JDBC）
 */
package com.equne.JDBC.jdbc_6_ATM.dao;

import com.equne.JDBC.jdbc_6_ATM.domain.Atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AtmDao {

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/atm";
    String user = "equne";
    String password = "1234";

    /*
        方法：负责查询一行记录
        * 参数：aname
        * 返回值：Atm对象
     */
    public Atm selectOne(String aname){
        Atm atm = null;
        String sql = "SELECT aname, apassword, abalance FROM atm WHERE aname = ?";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1, aname);
            ResultSet rs = pstat.executeQuery();
            if(rs.next()){
                atm = new Atm();
                atm.setAname(rs.getString("aname"));
                atm.setApassword(rs.getString("apassword"));
                atm.setAbalance(rs.getFloat("abalance"));
            }
            conn.close();
            pstat.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return atm;
    }


    /*
           新增一行记录
     */
    public int insert(Atm atm){
        int count = 0; // 数据库更新的行数 == 1
        String sql = "INSERT INTO atm(aname, apassword, abalance) VALUES(?, ?, ?)";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1, atm.getAname());
            pstat.setString(2, atm.getApassword());
            pstat.setFloat(3, atm.getAbalance());
            count = pstat.executeUpdate();
            conn.close();
            pstat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


}
