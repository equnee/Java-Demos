package com.equne.JDBC.jdbc_7_ConnectionPool.pool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *  摘要："连接池"类
 *   - 将好多连接存储起来 —— 集合 属性
 *   - 存储以后需要将连接获取到并给用户拿去使用 —— 需要管理池子
 */
public class ConnectionPool {

    // 属性 —— 大池子  list（遍历方便）
    // - 两个连接：1.Conn 2.MyConn(✅）
    private List<MyConnection> pool = new ArrayList();


    // 一个普通块 —— 给连接池赋值
    {
        for (int i = 1; i<=5; i++){
            pool.add(new MyConnection());
        }
    }


    // 方法 —— 获取连接，返回值: 1.Conn 2.MyConn(✅)
    // 从用户使用的角度来看：1和2都行
    // 用户需要操作状态，用户使用完后不能关闭，需要切换状态，释放连接。：2
    public MyConnection getMC(){
        MyConnection result = null;
        for (MyConnection mc : pool) {
            if (!mc.isUsed()) { // 连接可用
                mc.setUsed(true); // 占用连接
                result = mc;
                break;
            }
        }
            return result;
    }

}


