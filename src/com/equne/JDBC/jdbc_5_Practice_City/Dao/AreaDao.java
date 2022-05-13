package com.equne.JDBC.jdbc_5_Practice_City.Dao;

import com.equne.JDBC.jdbc_5_Practice_City.domain.Area;
import com.equne.JDBC.jdbc_5_Practice_City.domain.Country;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AreaDao {

    private String classname = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/demos";
    private String user = "equne";
    private String password = "1234";





    /*
        select1：人口范围 —— 查询人口数在1000到2000之间的城市所属在哪个地区
        * 参数：人口范围（1000-2000）
        * 返回值：几个不同的地区名 ArrayList<String>
     */
    public ArrayList<String> select1(int begin, int end){
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT aname FROM area a \n" +
                "INNER JOIN city i ON a.aid = i.aid \n" +
                "WHERE citysize BETWEEN ? AND ?";

        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setInt(1, begin);
            pstat.setInt(2,end);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()){
                String aname = rs.getString("aname");
                list.add(aname);
            }
            rs.close();
            conn.close();
            pstat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
