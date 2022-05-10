package com.equne.JDBC.jdbc_atmProject;

import com.equne.JDBC.jdbc_atmProject.test.TestAtm;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        TestAtm ta = new TestAtm();
        System.out.println("请输入用户名");
        String aname = input.nextLine();
        System.out.println("请输入密码");
        String apassword = input.nextLine();
        String result = ta.login(aname, apassword);
        if(result.equals("登录成功")){
            System.out.println(aname + "，您好！欢迎进入蜜桃银行系统 ");
            System.out.println("请输入操作选项序号");
            System.out.println("1.查询 2.存款 3.取款 4.转账");
            String option = input.nextLine();
            switch (option){
                case "1":
                    float abalance = ta.check(aname);
                    System.out.println("尊敬的"+ aname + "用户，您的可用余额为：" + abalance);
                    break;
                case "2":
                    System.out.println("功能完善中..");
                    break;
                case "3":
                    System.out.println("功能完善中..");
                    break;
                case "4":
                    System.out.println("功能完善中..");
                    break;
                default:
                    System.out.println("请输入正确序号！");

            }
        } else {
            System.out.println(result);
        }
    }

}

