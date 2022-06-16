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

    /**
     *  单例模式（连接池对象）：双重检测模型 + volatile关键字
     */
    // 设计一个单例设计模式 （只有在当前类内部才能创建该对象）
    // 1. 构造方法私有，为了从源头上截断对象的创建
    private ConnectionPool() {}
    // 2. 当前类内部创建一个唯一对象 new ConnectionPool();
    //     属性(✅) —— 私有的 静态的 当前类的对象作为属性
    //     方法(x：调用时变量都是临时性的)   构造方法(x：本身私有)  代码块(x：没有返回值，对象拿不走)
    private volatile static ConnectionPool connectionPool; // 💦️ 🍞 饿汉式:  = new ConnectionPool()
    // 3. 需要提供一个方式，让外面的人访问到我的属性对象（外界唯一能调用的方式）
    //    方法(✅) —— 公有的(能访问) 静态的(无需对象就能访问) 的方法 (目的为了返回那个唯一的对象)
    public static ConnectionPool getInstance(){
        if(connectionPool == null){ // 🍞 懒汉式：需要使用时才创建，减少服务器承载压力
            synchronized(ConnectionPool.class){ // 🔒 锁定创建过程时无法确定对象是否为空，因此在添加一个空检测
                if(connectionPool == null) {// 🔒 双重检测：锁定模版后，对象还是空的，说明对象没有问题，再进行赋值操作
                    connectionPool = new ConnectionPool(); // 💦️ 双重检测判断时没有影响，但赋值时可能会产生指令重排序的问题 —— 创建对象：①开辟内存空间 ②摆放信息 ③信息初始化赋值 ④调用方法告知创建完毕 ⑤把地址引用交还我们（都会产生冲突
                    // 💦️ 防止创建对象时指令重排序：为该属性添加 volatile
                }
            }
        }
        return connectionPool;
    }


    // 属性 —— 最小连接个数
    private int minConnectCount = Integer.parseInt(ConfigReader.getPropertyValue("minConnectCount"));
    private int waitTime = Integer.parseInt(ConfigReader.getPropertyValue("waitTime"));

    // 属性 —— 大池子  list（遍历方便）
    // - 两个连接：1.Conn 2.MyConn(✅）
    private List<MyConnection> pool = new ArrayList();


    // 一个普通块 —— 给连接池赋值
    {
        for (int i = 1; i <= minConnectCount; i++){
            pool.add(new MyConnection());
        }
    }


    // 方法 —— 获取连接，返回值: 1.Conn 2.MyConn(✅)
    // 从用户使用的角度来看：1和2都行
    // 用户需要操作状态，用户使用完后不能关闭，需要切换状态，释放连接。：2
    private Connection getMC(){ // 🔒 1：此处更推荐，因为性能不影响
        Connection result = null;
        for (Connection conn : pool) { // 循环找连接，因此循环次数耗费的时间微乎其微，因此没必要在循环内锁定。
            MyConnection mc = (MyConnection) conn; // 造型，夫类的引用指向子类对象
            if (mc.isUsed() == false) { // 连接可用
                synchronized(this){ //  🔒 2 (1)占用之前先锁定连接池对象(锁内部需判断两次）
                    if(mc.isUsed() == false){ // 🔒 2 (2)再次判断
                        mc.setUsed(true); // 占用连接
                        result = mc;
                    }
                }
                break;
            }
        }
            return result;
    }


    // 重新设计一个新的方法：用户等待机制
    // 最终目的：获取连接
    public Connection getConnection(){
        Connection result = this.getMC();
        int count = 0; // 记录循环次数（次数刚好能计算出时间）
        while(result == null && count < waitTime*10){ // 执行太快，让其休眠一会儿，释放空间
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = this.getMC();
            count++;
        }
        if(result == null){
            // 超过了5秒钟，还是没有找到
            // 自定义异常：系统繁忙，请稍后再试
            throw new SystemBusyException("系统繁忙，请稍后重试。");
        }
        return result;
    }

}


