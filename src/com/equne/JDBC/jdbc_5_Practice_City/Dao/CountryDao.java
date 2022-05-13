package com.equne.JDBC.jdbc_5_Practice_City.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CountryDao {

    private String classname = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/demos";
    private String user = "equne";
    private String password = "1234";


    /*
    select11：国家名称 —— 查询人口总数超过5000的国家名称
     * 参数：人口总数（>5000)
     * 返回值：国家名称、国家人口总数 ArrayList<String>
 */
    public ArrayList<String> select11(int sumCitySize){
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT c.cname FROM country c \n" +
                "INNER JOIN area a ON c.cid = a.cid \n" +
                "INNER JOIN city ci ON a.aid = ci.aid\n" +
                "GROUP BY c.cname\n" +
                "HAVING sum(ci.citysize) > ?";

        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setInt(1, sumCitySize);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()){
                String cname = rs.getString("cname");
                list.add(cname);
            }
            rs.close();
            conn.close();
            pstat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    /*
        Select2： —— 2. 查询每个国家的城市个数按照城市个数升序排列
         参数：无
         返回值：ArrayList<HashMap<String, String>>
     */
    public ArrayList<HashMap<String ,String>> select2(){
        ArrayList<HashMap<String ,String>> list = new ArrayList<>();
        String sql = "SELECT c.cname, COUNT(ci.cityid) AS citycount \n" +
                "FROM country c \n" +
                "INNER JOIN area a ON c.cid = a.cid \n" +
                "INNER JOIN city ci ON a.aid = ci.aid\n" +
                "GROUP BY c.cname\n" +
                "ORDER BY citycount;";

        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()){
                HashMap<String, String> map = new HashMap<>();
                map.put("cname", rs.getString("cname"));
                map.put("citycount", rs.getString("citycount"));
                list.add(map);
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
