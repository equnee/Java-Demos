package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

/**
 *    当执行的SQL语句与调用的代码不匹配时，抛出该自定义异常。
 *     insert("DELETE...")
 */
public class SqlFormatException extends RuntimeException{
    // 无参构造器
    public SqlFormatException(){
        super();
    }

    // 有参构造器
    public SqlFormatException(String msg){
        super(msg);
    }
}
