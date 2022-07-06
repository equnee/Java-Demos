package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations.Delete;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations.Insert;
import com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
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
     *  获得代理：创建dao接口的实现类（代理类）
     */
    public <T> T createDaoImpl(Class<T> interfaceType){
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class[]{interfaceType},
                new DaoInvocationHandler()
        ); // 参数：1-类加载器,生成的位置  2-实现的接口  3-实现代理调用的机制(策略模式)
    }

    /**
     * 定义Dao代理要如何实现功能（策略）
     */
    class DaoInvocationHandler implements InvocationHandler{
        /*
            - proxy：当前的代理对象，不要操作，会出现死循环。
            - method：代理当前要执行的接口中继承的方法，可以获得sql和返回类型信息。
            - args：调用方法时传递的参数。
         */
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 获得代理对象后，根据接口方法调用代理对象的方法时，就会执行当前的方法。
                        /*
                          Atm atm = new Atm("laoeight", "888", 800F);
                          JdbcFront front = new JdbcFront();
                          IAtmDao dao = front.createDaoImpl(IAtmDao.class); // 产生代理（生成AtmDao的实现类）
                          dao.save(atm); ——> invoke() // 在save里干什么，就在invoke里干什么

                            —> 根据调用的方法，执行相应的jdbc操作
                               执行jdbc操作时，需要：sql、参数、返回类型
                                - sql：可以通过接口方法上的注解获得（也可以使用'sql.properties'获得）
                                - 返回类型：可以通过接口方法的返回类型
                                - 参数：invoke方法中的args就是参数
                         */

            Annotation a = method.getAnnotations()[0]; // 获得接口方法上的注解
            Method m = a.getClass().getMethod("value"); // 获得注解中的value属性方法
            String sql = (String) m.invoke(a); // 调用当前注解的方法，得到sql
            Object param = args == null?null:args[0]; // if

            Object returnValue = null; // 增删改查的返回值：集合
            if(a.annotationType() == Insert.class){
                returnValue = insert(sql, param);
            } else if(a.annotationType() == Update.class){
                returnValue = update(sql, param);
            } else if(a.annotationType() == Delete.class){
                returnValue = delete(sql, param);
            } else{
                // 查询操作：需要考虑是集合查询还是单记录查询，与返回类型有关。
                Class rt = method.getReturnType();
                if(rt == List.class){
                    // 返回类型是List集合，查询多条记录，集合的范型是结果类型。
                    // 如何获得范型：
                    Type type = method.getGenericReturnType(); // 获得完整的返回类型：List<Atm>（type类型是所有范型类型的父类型，如class）
                    // 类型种类：class类型 / 接口类型 / 普通类型 / parameterizedType参数类型 ⬆（带范型的类型也继承于type）
                    ParameterizedType pt = (ParameterizedType)type;
                    Class fx = (Class)pt.getActualTypeArguments()[0]; // 获得范型，以数组的形式返回。[0]：当前返回类型中只有1个范型
                    returnValue = selectList(sql, fx, param);
                } else{
                    // 返回类型是domain，查询一条，返回类型即查询的结果类型。
                    returnValue = selectOne(sql, rt, param);
                }
            }
            return returnValue;
        }
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
        if(param != null){
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
