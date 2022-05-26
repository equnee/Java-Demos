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

    /*
        存款
     */
    public int deposit(String aname, Float depositAmount){
        Atm atm = dao.selectOne(aname);
        atm.setAbalance(atm.getAbalance() + depositAmount);
        return dao.update(atm);
    }


    /*
        取款
     */
    public int withdraw(String aname, Float withdrawalAmount){
        Atm atm = dao.selectOne(aname);
        if(atm.getAbalance()>=withdrawalAmount){
            atm.setAbalance(atm.getAbalance() - withdrawalAmount);
            return dao.update(atm); // 成功更新1行返回1，未更新返回0
        }else{
            return -1;
        }

    }

    /*
        转账
     */
    public int transfer(String outName, String inName, Float transferAmount){
        int count = 0;
        Atm outAtm = dao.selectOne(outName);
        Atm inAtm = dao.selectOne(inName);
        if(outAtm.getAbalance() >= transferAmount){
            outAtm.setAbalance(outAtm.getAbalance() - transferAmount);
            inAtm.setAbalance(inAtm.getAbalance() + transferAmount);
            return dao.update(outAtm) + dao.update(inAtm); // 2成功
        } else {
            return -1; // 余额不足
        }
    }

    /*
        销户
     */
    public int cancelAccount(String aname){
        return dao.delete(aname);
    }

}
