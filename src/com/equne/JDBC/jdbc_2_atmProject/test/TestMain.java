package com.equne.JDBC.jdbc_2_atmProject.test;

import com.equne.JDBC.jdbc_2_atmProject.Service.AtmService;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        AtmService ta = new AtmService();
        System.out.println("请输入用户名");
        String aname = input.nextLine();
        System.out.println("请输入密码");
        String apassword = input.nextLine();
        String result = ta.login(aname, apassword);
        if(result.equals("登录成功")){
            System.out.println(aname + "，您好！欢迎进入蜜桃银行系统 ");
            while(true){
                System.out.println("-------------------------------------\n" + ">> 请输入操作选项序号");
                System.out.println("1:查询  2:存款  3:取款  4:转账  \n5:开户  6:销户  886:退出");
                String option = input.nextLine();
                if (option.equals("886")){
                    System.out.println("您已退出系统，再见！");
                    break;
                }
                switch (option){
                    case "1":  // 1. 查询
                        float abalance = ta.check(aname);
                        System.out.println("尊敬的"+ aname + "用户，您的可用余额为：" + abalance);
                        break;
                    case "2":  // 2. 存款
                        System.out.println("请输入存款额：");
                        String depoistAmount = input.nextLine();
                        ta.deposit(aname, Float.parseFloat(depoistAmount));
                        System.out.println("您的可用余额为：" + ta.check(aname));
                        break;
                    case "3":  // 3. 取款
                        System.out.println("请输入取款额：");
                        String withdrawalAmount = input.nextLine();
                        ta.withdraw(aname, Float.parseFloat(withdrawalAmount));
                        System.out.println("您的可用余额为：" + ta.check(aname));
                        break;
                    case "4":  // 4. 转账
                        System.out.println("请输入收款人信息：");
                        String payee = input.nextLine();
                        System.out.println("请输入转账金额：");
                        String transferAmount = input.nextLine();
                        ta.transfor(aname, payee, Float.parseFloat(transferAmount));
                        System.out.println("您的可用余额为：" + ta.check(aname));
                        break;
                    case "5":  // 5. 开户
                        System.out.println("请输入开户账户名：");
                        String newAname = input.nextLine();
                        System.out.println("请输入新账户密码：");
                        String newApassword = input.nextLine();
                        System.out.println("请输入存入金额：");
                        String newAbalance = input.nextLine();
                        ta.newBank(newAname, newApassword, Float.parseFloat(newAbalance));
                        System.out.println("开户成功！新账户" + newAname + "当前可用余额为：" + ta.check(newAname));
                        break;
                    case "6":  // 6. 销户
                        System.out.println("请输入销户账户名：");
                        String cancelName = input.nextLine();
                        ta.cancelBank(cancelName);
                        break;
                    default:
                        System.out.println("请输入正确序号！");
                }
            }

        } else {
            System.out.println(result);
        }
    }

}

