package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.domain;

public class Atm {

    /* domain 实体：只是为了存储表格中的数据
         类 —— 表
        对象 —— 行
        属性 —— 列
        类型 —— 类型 */


    private String aname;
    private String apassword;
    private Float abalance;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ATM [");
        stringBuilder.append("aname:");
        stringBuilder.append(aname);
        stringBuilder.append(", ");
        stringBuilder.append("apassword:");
        stringBuilder.append(apassword);
        stringBuilder.append(", ");
        stringBuilder.append("abalance:");
        stringBuilder.append(abalance);
        stringBuilder.append("]");
        return new String(stringBuilder);
    }

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
