package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  jdbc查询操作：继承jdbc模版，按照jdbc的步骤执行查询操作。(JdbcTemplate类的具体子类对象）
 */
public class JdbcQuery extends JdbcTemplate {
    @Override
    protected Object executeSql() throws SQLException {
        ResultSet rs = pstat.executeQuery();
        // 将表结构中的数据转存到 List<Map>集合中
        // List - 表中所有记录
        // Map - 表中一条记录
        List<Map<String, Object>> results = new ArrayList<>();
        while (rs.next()){
            // 每次获取一条记录，装进一个Map
            Map<String, Object> map = new HashMap<>();
            // 循环当前记录
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String key = rs.getMetaData().getColumnName(i); // getMetaData：获取表结构信息。getColumnName：获取当前属性名
                Object value = rs.getObject(i);
                map.put(key.toUpperCase(), value);
            }
            results.add(map);
        }

        // System.out.println(results);
        return results;
    }
}
