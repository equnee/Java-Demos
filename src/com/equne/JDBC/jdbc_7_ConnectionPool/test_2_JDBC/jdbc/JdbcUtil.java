package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  jdbc工具：专门负责实现jdbc交互过程 (dao使用此工具）
 *
 *   1. executeUpdate：实现增删改操作
 *   2. executeQuery：实现查询操作
 */
public class JdbcUtil {
    /** 内部使用 */
    // 实现增删改
    private int executeUpdate(String sql, Object...params){
        return (int) new JdbcUpdate().execute(sql, params);
    }
    // 实现查询
    private List<Map<String, Object>> executeQuery(String sql, Object...params){
        return (List<Map<String, Object>>) new JdbcQuery().execute(sql, params);
    }


    /** 对外提供 */
    // 增
    public int insert(String sql, Object...params){
        // 检验实际传入的sql语句开头确实为"INSERT"，才执行插入。
        if(sql.substring(0,6).equalsIgnoreCase("insert")){
            return this.executeUpdate(sql, params);
        }
        // 实际不为插入语句，抛出自定义异常。
        throw new SqlFormatException("Not a insert statement: [" + sql + "]");
    }

    // 删
    public int delete(String sql, Object...params){
        if(sql.substring(0,6).equalsIgnoreCase("delete")){
            return this.executeUpdate(sql, params);
        }
        throw new SqlFormatException("Not a delete statement: [" + sql + "]");
    }

    // 改
    public int update(String sql, Object...params){
        if(sql.substring(0,6).equalsIgnoreCase("update")){
            return this.executeUpdate(sql, params);
        }
        throw new SqlFormatException("Not a update statement: [" + sql + "]");
    }

    // 查（多条）
    public List<Map<String, Object>> selectListMap(String sql, Object...params){
        if(sql.substring(0,6).equalsIgnoreCase("select")){
            return this.executeQuery(sql, params);
        }
        throw new SqlFormatException("Not a select statement: [" + sql + "]");
    }

    // 查（单条）
    public Map<String, Object> selectMap(String sql, Object...params){
        if(sql.substring(0,6).equalsIgnoreCase("select")){
            List<Map<String, Object>> rows = this.executeQuery(sql, params);
            if(rows!=null && rows.size()==1){
                return rows.get(0);
            } else if(rows == null){
                return null;
            } else{
                throw new RowCountException("Need one or null but return:" + rows);
            }
        }
        throw new SqlFormatException("Not a select statement: [" + sql + "]");
    }
}
