package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import com.equne.JDBC.jdbc_7_ConnectionPool.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc模版：负责指定jdbc执行的步骤
 *
 *   1. execute（模版模式）
 */
public abstract class JdbcTemplate {

    // 成员属性
    protected Connection conn;
    protected PreparedStatement pstat;
    protected ResultSet rs;

    public Object execute(String sql, Object...params){
        try {
            // 1，导包  2. 加载驱动类（ConnectionPool）
            // 3. 获取连接
            createConnection();
            // 4. 创建状态参数
            createStatement(sql, params);
            // 5. 执行sql语句
            Object result = executeSql();
            // 6. 关闭
            close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  3 - 从连接池中获取连接
     */
    private void createConnection(){
        ConnectionPool pool = ConnectionPool.getInstance();
        conn = pool.getConnection();
    }

    /**
     *  4 - 创建状态参数 + 传参
     */
    private void createStatement(String sql, Object...params) throws SQLException {
        // 可变参数（动态数组）
        pstat = conn.prepareStatement(sql);
        // 理论上：params数组中有几个数据，就说明sql中有几个"?"
        for (int i = 0; i < params.length; i++) {
            pstat.setObject(i+1, params[i]);;
        }
    }

    /**
     *  5 - 抽象方法（没有方法体，方法不具体） <模版模式>
     * @returm
     *  增删改 — int — 表示结果影响的行数
     *  查询 — 各种类型 — 返回查询结果
     */
    protected abstract Object executeSql() throws SQLException;

    // 6 - 关闭
    private void close() throws SQLException {
        if(rs != null){
            rs.close();
        }
        if(conn != null){
            conn.close();
        }
        if(pstat != null){
            pstat.close();
        }
    }


}
