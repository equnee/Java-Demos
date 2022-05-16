package com.equne.JDBC.jdbc_6_ATM.view;

import com.equne.JDBC.jdbc_6_ATM.util.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends BaseFrame {

    // 构造方法
    public LoginFrame(){
        this.init();
    };
    public LoginFrame(String title){
        super(title);
        this.init(); // 规定启动流程
    };


    // 添加一些属性：登录窗口上的各种属性
    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel(); // logo
    private JLabel titleLabel = new JLabel("请您登录系统"); // title
    private JLabel accountLabel = new JLabel("请输入账号：");
    private JTextField accountField = new JTextField(); //用来输入账号的文本框
    private JLabel passwordLabel = new JLabel("请输入密码：");
    private JPasswordField passwordField = new JPasswordField(); //用来输入密码的文本框
    private JButton loginButton = new JButton("登 录");
    private JButton registButton = new JButton("注 册");


    protected void setFontAndSoOn() {
        mainPanel.setLayout(null); // 设置panel布局为自定义
        logoLabel.setBounds(135, 40, 40, 40);
        logoLabel.setIcon(this.drawImage("src/com/equne/JDBC/jdbc_6_ATM/img/logo.png", 40, 40)); // 封装的方法
        titleLabel.setBounds(185, 40, 200, 40);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        accountLabel.setBounds(60,100, 140,40);
        accountLabel.setFont(new Font("宋体", Font.BOLD, 18));
        accountField.setBounds(170,105,260,32);
        accountField.setFont(new Font("宋体", Font.BOLD, 20));
        passwordLabel.setBounds(60,150, 140,40);
        passwordLabel.setFont(new Font("宋体", Font.BOLD, 18));
        passwordField.setBounds(170,155,260,32);
        passwordField.setFont(new Font("宋体", Font.BOLD, 20));
        loginButton.setBounds(120,210,100,38);
        loginButton.setFont(new Font("宋体", Font.BOLD, 14));
        loginButton.setBackground(Color.LIGHT_GRAY);
        registButton.setBounds(260,210,100,38);
        registButton.setFont(new Font("宋体", Font.BOLD, 14));
        registButton.setBackground(Color.LIGHT_GRAY);


    }

    protected void addElements() {
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(accountLabel);
        mainPanel.add(accountField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registButton);

        this.add(mainPanel);
    }

    // 绑定事件
    protected void addListener() {
        // 匿名内部类
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        registButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    protected void setFramSelf() {
        this.setBounds(400, 200, 500, 300); // 距离屏幕、自身大小500x300
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 退出时关闭
        this.setResizable(false); // 固定大小，不可拖拽
        this.setVisible(true); // 最终展示
    }


}
