package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


// + final-不能被继承，只能使用不能被修改（如继承重写方法）。
public final class ResultLoader<T> {

    // + private：私有 = 只能通过静态方法去加载
    private ResultLoader(){};

    //（实际上就是orm，改名为load） 给我类型type
    /**
     *  load()：将查询结果集中的一条map记录，组成指定的对象类型。（只有一个）
     */
    public static <T> T load(Map<String, Object> row, Class<T> type) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // ------------------------------------------------------------------------------------
        // 如果类型T是简单数据类型，即只有一个属性 ——> 取出即可
        if(type == Integer.class || type == int.class
                || type == Long.class || type == long.class
                || type == Double.class || type == double.class
                || type == String.class){
            // 只有一个字段属性，从map集合中取出一个集合 —> 类型未知，使用迭代。 //  values()取出其中一个，得到collection，无顺序没有下标 ——> for
            for(Object o :row.values()){ // 只有1个，遍历循环1次，直接返回
                return (T)o; // 转成对应类型
            }
        }
        // ------------------------------------------------------------------------------------
        // 走到此处：证明不是组成一个简单的类型，——> 那就是组成domain的对象类型。
        T obj = type.newInstance(); // 相当于new Atm();

        Method[] methods = type.getMethods(); // 通过反射找到了方法（不使用private私有属性，而使用方法寻找）
        for (Method method : methods){
            // 每次拿到方法的名字：作判断，若是set方法
            String mname = method.getName();
            if(mname.startsWith("set")){
                // 证明是set方法，可以赋值 ——> 通过set方法给属性赋值，属性值与数据库表对应。
                String key = mname.substring(3).toUpperCase(); // 截掉set —> aname —> ANAME
                Object value = row.get(key);
                if(value == null){
                    // 🔒 实体中有属性，但查询结果中没有，不需要赋值
                    continue;
                }

                // 🔐 走到此处：当前对象属性有对应的查询数据，需要通过当前set方法为属性赋值。
                // method.invoke(obj, value);// 相当于 atm.setAname(value);
                // 🍃【处理类型】：
                Class[] paramTypeArray = method.getParameterTypes(); // 获得set方法参数列表
                // 判断该set方法是否只有一个参数：
                if(paramTypeArray.length != 1){ // 若长度!=1,不是一个参数的set方法，不符合set方法赋值要求
                    continue;
                }
                // 正确属性：又有值，又只有一个参数。
                Class paramType = paramTypeArray[0]; // (此处一定有1个参数） ——> (强转）
                if(paramType == Integer.class || paramType == int.class){
                    method.invoke(obj, (Integer)value);// 相当于 atm.setAname(value);
                }else if(paramType == Long.class || paramType == long.class) {
                    method.invoke(obj, (Long)value);
                }else if(paramType == Double.class || paramType == double.class) {
                    method.invoke(obj, (Double)value);
                }else if(paramType == String.class){
                    method.invoke(obj, (String)value);
                }else{
                    // 其他类型暂时不考虑，但也不报错
                    continue;
                }
            }
        }
        return obj; // 结果：（将map变成domain）
    }
}
