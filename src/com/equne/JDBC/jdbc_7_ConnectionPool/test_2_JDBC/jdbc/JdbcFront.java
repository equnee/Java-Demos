package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  jdbc交互时前端操作对象：主要用来处理一些jdbc之前做的事情 —— 解析sql中的#{}部分，与对象参数匹配。
 *
 *  String sql = "INSERT INTO atm VALUES(#{aname}, #{apassword}, #{abalance})";
 *  util.insert(sql, car)
 *
 *   ——> 替换
 *
 *   String sql = "INSERT INTO atm VALUES(?, ?, ?, ?);
 *   JDBCUtil util = new JdbcUtil();
 *   util.insert(sql, atm.getAname(), atm.getApassword(), atm.getAbalance());
 *
 *   (简化sql穿参，今后只需要传递对象）
 */
public class JdbcFront {
    public int insert(String sql, Object param){
        try{
            SqlParseObject o = this.parseSql(sql, param);
            JdbcUtil util = new JdbcUtil();
            return util.insert(o.sql, o.values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int update(String sql, Object param){
        try{
            SqlParseObject o = this.parseSql(sql, param);
            JdbcUtil util = new JdbcUtil();
            return util.update(o.sql, o.values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String sql, Object param){
        try{
            SqlParseObject o = this.parseSql(sql, param);
            JdbcUtil util = new JdbcUtil();
            return util.delete(o.sql, o.values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public <T> List<T> selectList(String sql, Class<T> type,Object param){
        try{
            SqlParseObject o = this.parseSql(sql, param);
            JdbcUtil util = new JdbcUtil();
            return util.selectList(o.sql, type, o.values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public <T> T selectOne(String sql, Class<T> type,Object param){
        try{
            SqlParseObject o = this.parseSql(sql, param);
            JdbcUtil util = new JdbcUtil();
            return util.selectOne(o.sql, type, o.values);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // -----------------------------------------------------------------------------------

    /**
     * 解析sql：#{} —> ?
     * @param sql
     * @param param
     */
    private SqlParseObject parseSql(String sql, Object param) throws Exception {
        // 装载sql语句中#{}指定属性 key
        List<String> paramNameArray = new ArrayList<>();

        // 遍历sql中的 #{}
        while(true){
            int i1 = sql.indexOf("#{");
            int i2 = sql.indexOf("}");
            if(i1 != -1 && i2 != -1 && i1 < i2){
                // 找到一个#{}
                String key = sql.substring(i1+2, i2).trim();
                paramNameArray.add(key);
                // 将当前的#{}替换成 ?
                sql = sql.substring(0, i1) + "?" + sql.substring(i2+1);
            } else {
                // 没找到 #{}
                break;
            }
        }
//        System.out.println(sql);
//        System.out.println(paramNameArray);
        // ------------------------------------------------------------------------------------
            /*
                根据sql中指定的参数顺序，从传递的参数对象中获取对应参数值，并按照数据顺序组成数组。
                - 经分析，参数可能会有3种情况
                    * 简单参数：int，double，string
                    * map
                    * domain
             */
        List<Object> values = new ArrayList<>(); // 装参数值
        Class paramType = param.getClass();
        if(paramType == Integer.class || paramType == int.class
                || paramType == Double.class || paramType == double.class
                || paramType == Long.class || paramType == long.class
                || paramType == String.class ){
            // 🍋(1) 是简单类型，只有1个值 —— sql中的参数应该也是1个。
            values.add(param);
        } else if(paramType == Map.class){
            // 🍋(2) map类型
            for(String paramName:paramNameArray){
                // sql中参数名：map中的key
                Map map = (Map) param;
                Object value = map.get(paramName);
                values.add(value);
            }
        } else {
            // 🍋(3) domain对象
            for (String paramName:paramNameArray) {
                // sql中参数名：domain对象中的属性名 ——> get方法（aname —> getAname）
                String mname = "get" + paramName.substring(0,1).toUpperCase() + paramName.substring(1); // 有问题： a—>getA()，做个判断
                Method method = paramType.getMethod(mname);
                Object value = method.invoke(param);
                values.add(value);
            }
        }
        return new SqlParseObject(sql, values.toArray());
    }

    // ------------------------------------------------------------------------------------
    // 内部类
    private class SqlParseObject{
        String sql; // 装载解析后，将3变成？的sql
        Object[] values; // 装载与sql中#指定的参数相同顺序的参数值

        // 构造器：
        public SqlParseObject(String sql, Object[] values) {
            this.sql = sql;
            this.values = values;
        }
    }
}
