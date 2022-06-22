package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.sql.SQLException;

/**
 *  jdbc更新操作：继承jdbc模版，按照jdbc的步骤执行增删改操作。(JdbcTemplate类的具体子类对象）
 */
public class JdbcUpdate extends JdbcTemplate {
    @Override
    protected Object executeSql() throws SQLException {
        return pstat.executeUpdate();
    }
}
