package com.equne.JDBC.jdbc_2_atmProject.Service;

import com.equne.JDBC.jdbc_2_atmProject.Dao.AtmDao;
import com.equne.JDBC.jdbc_2_atmProject.domain.Atm;

public class AtmService {

    /*
     *    以下方法为处理业务逻辑：比较、判断、计算，看不到数据库的读写操作（JDBC）。
     *
     *      Service业务层需要Dao持久层的支持 — 在Service层中存储一个Dao层的对象作为属性。
     *      可以使用注解 @MyAnnotation("dao.AtmDao") ——> IOC思想（对象交给别人来控制）
     **/

    private AtmDao dao = new AtmDao();

    // login: 登录
    public String login(String aname, String apassword){
        Atm atm = dao.selectOne(aname);
        if(atm!=null && atm.getApassword().equals(apassword)){
            return "登录成功";
        }
        return "用户名或密码错误";
    }

    /* check: 查询余额
     */
    public float check(String aname){
        // this.的写法省略了'Atm atm ='的过程。
        return dao.selectOne(aname).getAbalance();
    }

    /* deposit: 存款
        updade，修改数据库内的信息
     */
    public void deposit(String aname, float depositAmount){
        Atm atm = dao.selectOne(aname);
        if(depositAmount > 0){
            atm.setAbalance(atm.getAbalance() + depositAmount) ;
            dao.update(atm);
            System.out.println("存款成功。");
        }else {
            System.out.println("存款失败，存款额有误。");
        }

    }

    /* withdraw：取款
       updade，修改数据库内的信息
     */
    public void withdraw(String aname, float withdrawalAmout){
        Atm atm = dao.selectOne(aname);
        if(atm.getAbalance() >= withdrawalAmout){
            atm.setAbalance(atm.getAbalance() - withdrawalAmout);
            dao.update(atm);
            System.out.println("取款成功。");
        } else {
            System.out.println("取款失败，余额不足。");
        }
    }

    /* transfer： 转账
     */
    public void transfor(String remitter, String payee, float transferAmount){
        Atm atmRemitter = dao.selectOne(remitter);
        Atm atmPayee = dao.selectOne(payee);
        if (atmPayee.getAname().equals(payee)){
            if(transferAmount <= atmRemitter.getAbalance() && transferAmount != 0){
                atmRemitter.setAbalance(atmRemitter.getAbalance() - transferAmount);
                atmPayee.setAbalance(atmPayee.getAbalance() + transferAmount);
                dao.update(atmRemitter);
                dao.update(atmPayee);
                System.out.println("转账成功！");
            } else {
                System.out.println("转账失败，余额不足。");
            }
        } else {
            System.out.println("转账失败，收款人信息有误。");
        }

    }

    // 开户
    public void newBank(String aname, String apassword, float abalance){
        dao.insert(new Atm(aname, apassword, abalance));
    }

    // 销户
    public void cancelBank(String aname){
        // 查找要销户的账户
        Atm atm = dao.selectOne(aname);
        // 若有该账户则销户
        if(atm!=null){
            dao.delete(aname);
            System.out.println("已成功删除该账户！");
        } else {
            System.out.println("销户失败，未查询到该账户");
        }
    }
}
