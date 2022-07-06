package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao;

import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.domain.Atm;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.JdbcFront;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.JdbcUtil;

import java.util.List;

public class AtmDao {

    /**
     *  新增
     */
    public void save(){
        Atm atm = new Atm("laoseven", "777", 700F);
        String sql = "INSERT INTO atm VALUES(#{aname}, #{apassword}, #{abalance})";
        JdbcFront front = new JdbcFront();
        front.insert(sql, atm);
    }

    /**
     *  修改
     */
    public void update(){

    }

    /**
     *  删除
     */
    public void delete(){
        String aname = "laosix";
        String sql = "DELETE FROM atm WHERE aname = ?";
        JdbcUtil util = new JdbcUtil();
        util.delete(sql, aname);

    }

    /**
     *  多条查询（匿名内部类 AtmMapper）
     */
    public void findAll(){
        String sql = "SELECT * FROM atm";
        JdbcUtil util = new JdbcUtil();
        // 1. 直接输出：util.selectListMap(sql);
        // 2. 使用 mapper 实现具体输出：（自己动手）
//        List<Atm> atms = util.selectList(sql, new Mapper<Atm>() {
//            @Override
//            public Atm orm(Map<String, Object> row) {
//                String aname = (String) row.get("ANAME");
//                String apassword = (String) row.get("APASSWORD");
//                Float alabance = (Float) row.get("ABALANCE");
//                return new Atm(aname, apassword, alabance);
//            }
//        });
        // 2.2: 让框架自己想办法实现具体输出。
        List<Atm> atms = util.selectList(sql, Atm.class);
        for(Atm atm: atms){
            System.out.println(atm);
        }
    }

    /**
     *  单条查询
     */
    public void findById(){
        String sql = "SELECT * FROM atm WHERE aname = #{1}";
        String aname = "lisi";
        JdbcFront front = new JdbcFront();
        Atm atm = front.selectOne(sql, Atm.class, aname);
        System.out.println(atm);
    }

    /**
     *  查找总数（匿名内部类TotalMapper）
     */
    public void findTotal(){
        String sql = "SELECT COUNT(*) total FROM atm";
        JdbcUtil util = new JdbcUtil();
//        long total = util.selectOne(sql, new Mapper<Long>() {
//            @Override
//            public Long orm(Map<String, Object> row) {
//                return (Long) row.get("TOTAL");
//            }
//        });
        long total = util.selectOne(sql, Long.class); // 我交给框架，让框架想办法组成Long
        System.out.println(total);
    }




    /**
     * 规则：实现查询结果组成Atm对象（实现接口） —— !! 可以使用匿名内部类代替。
     */
//    class AtmMapper implements Mapper<Atm>{
//        @Override
//        public Atm orm(Map<String, Object> row) {
//            String aname = (String)row.get("ANAME");
//            String apassword = (String)row.get("APASSWORD");
//            Float abalance = (Float)row.get("ABALANCE");
//            return new Atm(aname, apassword, abalance);
//        }
//    }


//    class TotalMapper implements  Mapper<Long>{
//        @Override
//        public Long orm(Map<String, Object> row) {
//            return (Long) row.get("TOTAL");
//        }
//    }

}
