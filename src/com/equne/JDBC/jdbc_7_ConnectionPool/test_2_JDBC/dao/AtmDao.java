package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao;

import com.equne.JDBC.jdbc_7_ConnectionPool.pool.ConnectionPool;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.domain.Atm;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtmDao {

    /**
     *  新增
     */
    public void save(){
        Atm atm = new Atm("laosix", "666", 6000F);
        String sql = "INSERT INTO atm values(?, ?, ?)";
        JdbcUtil util = new JdbcUtil();
        util.delete(sql, new Object[]{atm.getAname(), atm.getApassword(), atm.getAbalance()});
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
     *  多条查询
     */
    public void findAll(){
        String sql = "SELECT * FROM atm";
        JdbcUtil util = new JdbcUtil();
        util.selectListMap(sql);
    }

    /**
     *  单条查询
     */
    public void findById(){
        String sql = "SELECT * FROM atm WHERE aname = ?";
        String aname = "lisi";
        JdbcUtil util = new JdbcUtil();
        util.selectMap(sql, aname);
    }
}
