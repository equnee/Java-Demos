package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC;

import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao.AtmDao;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao.IAtmDao;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.domain.Atm;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.JdbcFront;

import java.util.List;

public class TestMain {
    public static void main(String[] args){
        // new AtmDao().save();
        // new AtmDao().delete();
        // new AtmDao().findAll();
        // new AtmDao().findById();
        // new AtmDao().findTotal();
        // new AtmDao().findTotal();

        // 测试代理类
        Atm atm = new Atm("lao8", "888", 800F);
        JdbcFront front = new JdbcFront();
        IAtmDao dao = front.createDaoImpl(IAtmDao.class); // 产生代理（生成AtmDao的实现类）
        // - 添加
        //dao.save(atm);
        // - 查询多条
        /* List<Atm> atms = dao.findAll();
        for (Atm a:atms) {
            System.out.println(a);
        }*/
        // - 查询单条
        Atm a = dao.findById("lao8");
        System.out.println(a);


    }
}
