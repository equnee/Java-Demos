/*
    业务逻辑：数据处理，如比较、判断、计算等
 */
package com.equne.JDBC.jdbc_6_ATM.service;

import com.equne.JDBC.jdbc_6_ATM.dao.AtmDao;
import com.equne.JDBC.jdbc_6_ATM.domain.Atm;
import com.equne.JDBC.jdbc_6_ATM.util.MySpring;

public class AtmService {

    // 帮我们管理对象，还能帮我们处理单例
    public AtmDao dao = MySpring.getBean("com.equne.JDBC.jdbc_6_ATM.dao.AtmDao");


    /*
          登录方法
     */
    public String login(String aname, String apassword){
        Atm atm = dao.selectOne(aname);
        if(atm!=null && atm.getApassword().equals(apassword)){
            return "登录成功";
        }
        return "用户名或密码错误";
    }

    /*
        查询余额方法
     */
    public Float check(String aname){
        return dao.selectOne(aname).getAbalance();
    }

    /*
        注册新用户方法（开户）
     */
    public int newBank(String aname, String apassword, Float abalance){
        Atm atm = new Atm(aname, apassword, abalance);
        return dao.insert(atm);
    }

    /*
        判断账号名是否存在
     */
    public boolean isExist(String aname){
        if(dao.selectOne(aname) != null){
            return true; // 账号已存在
        }
        return false; // 账号不存在，未注册
    }
}
