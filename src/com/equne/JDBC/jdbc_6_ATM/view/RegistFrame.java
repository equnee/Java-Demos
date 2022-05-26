package com.equne.JDBC.jdbc_6_ATM.view;

import com.equne.JDBC.jdbc_6_ATM.service.AtmService;
import com.equne.JDBC.jdbc_6_ATM.util.BaseFrame;
import com.equne.JDBC.jdbc_6_ATM.util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("all")
public class RegistFrame extends BaseFrame {

    // 0. 构造方法
    private RegistFrame(){
        super("注册窗口"); // 也可以在重载单例中创建带参数函数
        this.init();
    }
    // 单例模式 —— 在主窗口中点击注册只能显示一个窗口，不多次创建（开发Swing通常应写为单例模式）
    private static RegistFrame registFrame;
    public synchronized static RegistFrame getRegistFram(){
        if(registFrame ==null){
            registFrame = new RegistFrame();
        }
        return registFrame;
    }
//    public RegistFram(){
//        this.init();
//    }
//    public RegistFram(String title){ // 处理名头
//        super(title); // 交给父类完成
//        this.init();
//    }


    // 添加一个控制登录页面的属性
    private LoginFrame loginFrame = LoginFrame.getLoginFrame();

    // 2. 添加一些组件属性
    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel(); // logo
    private JLabel titleLabel = new JLabel("请您填写信息"); // title
    private JLabel accountLabel = new JLabel("请输入账号：");
    private JTextField accountField = new JTextField();
    private JLabel passwordLabel = new JLabel("请输入密码：");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel balanceLabel = new JLabel("请输入金额：");
    private JTextField balanceFiled = new JTextField();
    private JButton registButton = new JButton("注 册");
    private JButton resetButton = new JButton("重 置");
    private JButton backButton = new JButton("返 回");


    // 3. 设置组件样式
    protected void setFontAndSoOn() {
        mainPanel.setLayout(null); // 清除原有布局
        logoLabel.setBounds(135, 40, 40, 40);
        logoLabel.setIcon(this.drawImage("src/com/equne/JDBC/jdbc_6_ATM/img/logo.png", 40, 40));
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
        balanceLabel.setBounds(60,200, 140, 40);
        balanceLabel.setFont(new Font("宋体", Font.BOLD, 18));
        balanceFiled.setBounds(170,205,260,32);
        balanceFiled.setFont(new Font("宋体", Font.BOLD, 20));
        registButton.setBounds(80,260,100,38);
        registButton.setFont(new Font("宋体", Font.BOLD, 14));
        registButton.setBackground(Color.LIGHT_GRAY);
        resetButton.setBounds(200,260,100,38);
        resetButton.setFont(new Font("宋体", Font.BOLD, 14));
        resetButton.setBackground(Color.LIGHT_GRAY);
        backButton.setBounds(320,260,100,38);
        backButton.setFont(new Font("宋体", Font.BOLD, 14));
        backButton.setBackground(Color.LIGHT_GRAY);
    }


    // 4. 添加到窗体
    protected void addElements() {
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(accountLabel);
        mainPanel.add(accountField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(balanceLabel);
        mainPanel.add(balanceFiled);
        mainPanel.add(registButton);
        mainPanel.add(resetButton);
        mainPanel.add(backButton);

        this.add(mainPanel);
    }


    // 5. 为按钮添加监听器
    protected void addListener() {
        // 注册按钮
        registButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取新输入的信息：名字、密码、余额
                String aname = accountField.getText();
                String apassword = passwordField.getText();
                String abalance = balanceFiled.getText();
                // 底层调用新增方法
                AtmService service = MySpring.getBean("com.equne.JDBC.jdbc_6_ATM.service.AtmService");
                if(service.isExist(aname) == true){
                    JOptionPane.showMessageDialog(RegistFrame.this, "对不起，您输入的账号已存在。");
                    RegistFrame.this.reset();
                } else {
                    try {
                        service.newBank(aname, apassword, Float.valueOf(abalance));
                        JOptionPane.showMessageDialog(RegistFrame.this, "注册成功，请登录系统。");
                        RegistFrame.this.back();
                    } catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(RegistFrame.this, "对不起，您输入的金额有误。");
                        RegistFrame.this.reset();
                    }

                }
            }
        });
        // 重置按钮
        resetButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistFrame.this.reset(); // 匿名内部类：使用RegistFrame调用
            }
        });
        // 返回按钮
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistFrame.this.setVisible(false);
                loginFrame.setVisible(true); // 显示登录窗口
                loginFrame.reset(); // 清空输入框内容
            }
        });
    }
    // 自定义方法封装冗余代码：清空所有输入框内容
    void reset(){
        accountField.setText("");
        passwordField.setText("");
        balanceFiled.setText("");
    }
    // 自定义方法 封装冗余代码：返回登录页面
    private void back(){
        RegistFrame.this.reset();
        RegistFrame.this.setVisible(false); // 隐藏注册窗口
        loginFrame.setVisible(true);
    }

    // 1. 设置主窗体
    protected void setFrameSelf() {
        this.setBounds(430, 200, 500, 360); // 距离屏幕、自身大小500x300
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 点击关闭是隐藏当前窗体，不退出系统
        this.setResizable(false); // 固定大小，不可拖拽
        this.setVisible(true); // 最终展示
    }
}
