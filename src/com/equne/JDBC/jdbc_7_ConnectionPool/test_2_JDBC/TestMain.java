package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC;

import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao.AtmDao;

public class TestMain {
    public static void main(String[] args){
        // new AtmDao().save();
        // new AtmDao().delete();
        // new AtmDao().findAll();
        new AtmDao().findById();
    }
}
