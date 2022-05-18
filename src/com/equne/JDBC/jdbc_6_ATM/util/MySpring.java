/*
    模拟spring：为了管理对象的产生
     - IOC控制反转思想：将对象的控制权交给当前类来负责
     - 生命周期托管的方式：实现对对象的单例管理
 */
package com.equne.JDBC.jdbc_6_ATM.util;

import java.util.HashMap;

public class MySpring {
    // 属性：为了存储所有被管理的对象——集合  （静态元素是唯一存在的：集合、对象都是唯一的）
    private static HashMap<String, Object> beanBox = new HashMap<>();


    /* 方法：获取任何一个类的对象（类含有无参数的构造方法）
        * 参数：String（类全名）
        * 返回值：某个类的对象 —— 范型:使用不同类型接收
        * static：通过类名就可以调用
     */
    public static <T>T getBean(String className){
        T obj = null;
        try {
            // 1. 直接从 beanBox 集合中获取
            obj = (T)beanBox.get(className);
            // 2. 若obj为空 说明之前没有创建过对象
            if(obj == null){ // 控制单例模式，保证对象的唯一性
                // 3. 通过类名字获取 Class
                Class clazz = Class.forName(className);
                // 4. 通过反射创建一个对象
                obj = (T)clazz.newInstance();
                // 5. 将新的对象存入集合
                beanBox.put(className, obj);
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
