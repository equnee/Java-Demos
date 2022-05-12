package com.equne.JDBC.jdbc_3_StringInject.test;

import java.util.Scanner;

public class TestStringMain {
    public static void main(String[] args) {

        TestStringAtm atm = new TestStringAtm();
        Scanner input = new Scanner(System.in);
        System.out.println("欢迎进入银行系统：");
        System.out.println("请输入用户名：");
        String name = input.nextLine();
        System.out.println("请输入密码：");
        String pswd = input.nextLine();
        String result = atm.login(name, pswd);
        if (result.equals("登录成功")) {
            System.out.println(result);
            System.out.println("请输入操作编号：\n 1.查询 2.存款 3.取款 ...  ");
        } else {
            System.out.println(result);
        }

    }


}
