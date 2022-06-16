package com.equne.JDBC.jdbc_7_ConnectionPool.pool;

public class SystemBusyException extends RuntimeException{

    public SystemBusyException(){}
    public SystemBusyException(String message){
        super(message);
    }

}
