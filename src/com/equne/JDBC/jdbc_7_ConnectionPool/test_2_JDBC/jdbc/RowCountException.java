package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

/**
 * 当查询单挑记录时，却查出多条记录结果，抛出该异常
 *  selectMap("SELECT * FROM atm");
 */
public class RowCountException extends RuntimeException{
    public RowCountException(){
        super();
    }

    public RowCountException(String msg){
        super(msg);
    }
}
