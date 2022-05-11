/* abstract: 该文件为本项目最初版本，现两部分代码分别分层为：Dao目录中的AtmDao，与Service中的AtmService。
 * date: 2022-05-09
 *  */
package com.equne.JDBC.jdbc_atmProject.test;

import com.equne.JDBC.jdbc_atmProject.domain.Atm;

import java.sql.*;

public class TestAtm {

//    /* ————————————————————————————————————————————————————————————————————————
//     *    以下方法为纯粹的JDBC读写数据库操作，没有任何逻辑
//     *     * 对于atm表格中，单条记录的：新增，修改，删除，查询
//     *       - 新增、修改、删除 ——> 代码几乎一致（只差一条sql语句）——> 优化
//     *       - 查询 ——> 与别的类查询几乎一致（参数不同）
//     *     * 框架：Mybatis / Hibernate持久层 —— 专门负责读写数据库（DAO）
//     * ———————————————————————————————————————————————————————————————————————— */
//
//    /* delete: 负责将某行记录从数据库删除
//     */
//    public void delete(String aname){
//        String className = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/atm";
//        String user = "root";
//        String password = "rootroot";
//        Connection conn = null;
//        Statement stat = null;
//        String sql = "DELETE FROM atm WHERE aname ='" + aname +"'";
//        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//            stat.executeUpdate(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (conn!=null){
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(stat!=null){
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /* insert: 负责将新一行记录写入数据库
//     */
//    public void insert(Atm atm){
//        String className = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/atm";
//        String user = "root";
//        String password = "rootroot";
//        String sql = "INSERT INTO atm VALUES('" + atm.getAname() +"','" + atm.getApassword() + "'," + atm.getAbalance() +")";
//        Connection conn = null;
//        Statement stat = null;
//        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//            stat.executeUpdate(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (conn!=null){
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(stat!=null){
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /* update：连接数据库、做数据库的写操作
//        封装：存款/取款/修改密码操作代码，通用的update操作代码
//        参数：Atm / 返回值：无 (int更新行数)
//     */
//    public void update(Atm atm){
//        String className = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/atm";
//        String user = "root";
//        String password = "rootroot";
//        Connection conn = null;
//        Statement stat = null;
//        String sql = "UPDATE atm SET apassword = '" + atm.getApassword() + "', abalance = '" + atm.getAbalance() + "' WHERE aname ='" + atm.getAname() + "'";
//        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//            stat.executeUpdate(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭流：放在finally内必需执行。
//            // 分成三个try/catch分开关闭，增强健壮性：当某个流出现异常无法关闭时，其他流也能正常关闭。
//            try {
//                if (conn!=null){ // 若为空时关闭会抛出空指针异，因此限定当流创建成功即非空时才关闭。
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(stat!=null){
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /* selectOne：查询一条记录
//        封装：登录和查询的重复代码
//        参数：aname / 返回值：Object (domain) （返回值不能是ResultSet，因为程序最后会使用rs.close()将结果集关闭。可以将结果集中的数据取出来，将数据存在一个容器里返回。
//     */
//    public Atm selectOne(String aname) {
//        Atm atm = null;
//        String className = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/atm";
//        String user = "root";
//        String password = "rootroot";
//        String sql = "SELECT aname, apassword, abalance FROM atm WHERE aname = '" + aname + "'";
//        Connection conn = null;
//        Statement stat = null;
//        ResultSet rs = null;
//        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url, user, password);
//            stat = conn.createStatement();
//            rs = stat.executeQuery(sql);
//            if (rs.next()) {
//                atm = new Atm(); // 懒加载机制
//                atm.setAname(rs.getString("aname"));
//                atm.setApassword(rs.getString("apassword"));
//                atm.setAbalance(rs.getFloat("abalance"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭流：放在finally内必需执行。
//            // 分成三个try/catch分开关闭，增强健壮性：当某个流出现异常无法关闭时，其他流也能正常关闭。
//            try {
//                if (conn!=null){ // 若为空时关闭会抛出空指针异，因此限定当流创建成功即非空时才关闭。
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(stat!=null){
//                    stat.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(rs!=null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return atm;
//    }
//
//    // ————————————————————————————————————————————————————————————————————————
//    //    以下方法为处理业务逻辑：比较、判断、计算，看不到数据库的读写操作（JDBC）。
//    // ————————————————————————————————————————————————————————————————————————
//
//    // login: 登录
//    public String login(String aname, String apassword){
//        Atm atm = this.selectOne(aname);
//        if(atm!=null && atm.getApassword().equals(apassword)){
//            return "登录成功";
//        }
//        return "用户名或密码错误";
//    }
//
//    /* check: 查询余额
//     */
//    public float check(String aname){
//        // this.的写法省略了'Atm atm ='的过程。
//        return this.selectOne(aname).getAbalance();
//    }
//
//    /* deposit: 存款
//        updade，修改数据库内的信息
//     */
//    public void deposit(String aname, float depositAmount){
//        Atm atm = this.selectOne(aname);
//        if(depositAmount > 0){
//            atm.setAbalance(atm.getAbalance() + depositAmount) ;
//            this.update(atm);
//            System.out.println("存款成功。");
//        }else {
//            System.out.println("存款失败，存款额有误。");
//        }
//
//    }
//
//    /* withdraw：取款
//       updade，修改数据库内的信息
//     */
//    public void withdraw(String aname, float withdrawalAmout){
//        Atm atm = this.selectOne(aname);
//        if(atm.getAbalance() >= withdrawalAmout){
//            atm.setAbalance(atm.getAbalance() - withdrawalAmout);
//            this.update(atm);
//            System.out.println("取款成功。");
//        } else {
//            System.out.println("取款失败，余额不足。");
//        }
//    }
//
//    /* transfer： 转账
//     */
//    public void transfor(String remitter, String payee, float transferAmount){
//        Atm atmRemitter = this.selectOne(remitter);
//        Atm atmPayee = this.selectOne(payee);
//        if (atmPayee.getAname().equals(payee)){
//            if(transferAmount <= atmRemitter.getAbalance() && transferAmount != 0){
//                atmRemitter.setAbalance(atmRemitter.getAbalance() - transferAmount);
//                atmPayee.setAbalance(atmPayee.getAbalance() + transferAmount);
//                this.update(atmRemitter);
//                this.update(atmPayee);
//                System.out.println("转账成功！");
//            } else {
//                System.out.println("转账失败，余额不足。");
//            }
//        } else {
//            System.out.println("转账失败，收款人信息有误。");
//        }
//
//    }
//
//    // 开户
//    public void newBank(String aname, String apassword, float abalance){
//        this.insert(new Atm(aname, apassword, abalance));
//    }
//
//    // 销户
//    public void cancelBank(String aname){
//        // 查找要销户的账户
//        Atm atm = this.selectOne(aname);
//        // 若有该账户则销户
//        if(atm!=null){
//            this.delete(aname);
//        } else {
//            System.out.println("销户失败，未查询到该账户");
//        }
//    }
}
