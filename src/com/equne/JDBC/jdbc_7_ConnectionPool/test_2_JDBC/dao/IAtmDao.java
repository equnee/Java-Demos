package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.dao;

import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.domain.Atm;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations.Insert;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations.Select;

import java.util.List;

public interface IAtmDao {

    @Insert("insert into atm values(#{aname}, #{apassword}, #{abalance})")
    public void save(Atm atm);

    @Select("select * from atm")
    public List<Atm> findAll();

    @Select("select * from atm where aname=#{aname}")
    public Atm findById(String aname);
}


