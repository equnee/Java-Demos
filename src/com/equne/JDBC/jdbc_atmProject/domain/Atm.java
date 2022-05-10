package com.equne.JDBC.jdbc_atmProject.domain;

public class Atm {

    /* domain 实体：只是为了存储表格中的数据
         类 —— 表
        对象 —— 行
        属性 —— 列
        类型 —— 类型 */


    private String aname;
    private String apassword;
    private Float abalance;

    // 无参数构造方法
    public Atm() {}

    // 有参数构造方法
    public Atm(String aname, String apassword, Float abalance) {
        this.aname = aname;
        this.apassword = apassword;
        this.abalance = abalance;
    }

    // getter() & setter()
    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {
        this.apassword = apassword;
    }

    public Float getAbalance() {
        return abalance;
    }

    public void setAbalance(Float abalance) {
        this.abalance = abalance;
    }
}
