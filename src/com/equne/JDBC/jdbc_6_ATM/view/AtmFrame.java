package com.equne.JDBC.jdbc_6_ATM.view;

import com.equne.JDBC.jdbc_6_ATM.service.AtmService;
import com.equne.JDBC.jdbc_6_ATM.util.BaseFrame;
import com.equne.JDBC.jdbc_6_ATM.util.MySpring;

import javax.swing.*;
import java.awt.*;

public class AtmFrame extends BaseFrame {

    // 构造方法：单例模式
    private AtmFrame(String aname){
        super("银行系统");
        this.aname = aname;
        this.init();
    }
    private static AtmFrame atmFrame;
    public static AtmFrame getAtmFrame(String anme){
        if(atmFrame == null){
            atmFrame = new AtmFrame(anme);
        }
        return atmFrame;
    }

    // 添加一个用来管理当前用户的用户名
    private String aname;

    // 创建一个AtmService对象作为属性，支持所有业务：查询、存款、取款、转账
    private AtmService service = MySpring.getBean("com.equne.JDBC.jdbc_6_ATM.service.AtmService");

    // 创建窗体组件
    private JPanel mainPanel = new JPanel();
    private JLabel logoLabel = new JLabel();
    private JLabel titleLabel = new JLabel("蜜桃银行");
    private JLabel balanceLabelCN = new JLabel();
    private JLabel balanceLabelEN = new JLabel();
    private JLabel selectServerLabelCN = new JLabel("您好！请选择所需服务");
    private JLabel selectServerLabelEN = new JLabel("Please Select Service");
    private JButton messageButton = new JButton("个人信息");
    private JButton exitButton = new JButton("退出");
    private JButton depositButton = new JButton("存款");
    private JButton withdrawalButton = new JButton("取款");
    private JButton transferButton = new JButton("转账");


    protected void setFontAndSoOn() {
        mainPanel.setLayout(null);
        logoLabel.setBounds(20,20,80,80);
        logoLabel.setIcon(drawImage("src/com/equne/JDBC/jdbc_6_ATM/img/logo.png",80,80));
        titleLabel.setBounds(120,30,160,60);
        titleLabel.setFont(new Font("宋体", Font.ITALIC, 32));

        balanceLabelCN.setBounds(250,200,300,40);
        balanceLabelCN.setFont(new Font("宋体", Font.BOLD, 24));
        balanceLabelCN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelCN.setText("用户余额：¥" + service.check(aname));
        balanceLabelEN.setBounds(250,240,300,40);
        balanceLabelEN.setFont(new Font("宋体", Font.BOLD, 22));
        balanceLabelEN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelEN.setText("Account Balance：¥" + service.check(aname));

        selectServerLabelCN.setBounds(320,370,300,40);
        selectServerLabelCN.setFont(new Font("宋体", Font.PLAIN, 16));
        selectServerLabelEN.setBounds(320,400,300,40);
        selectServerLabelEN.setFont(new Font("宋体", Font.PLAIN, 16));

        messageButton.setBounds(10,150,120,40);
        messageButton.setFont(new Font("宋体", Font.BOLD, 14));
        messageButton.setBackground(Color.LIGHT_GRAY);
        messageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton.setBounds(10,390,120,40);
        exitButton.setFont(new Font("宋体", Font.BOLD, 14));
        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        depositButton.setBounds(670,150,120,40);
        depositButton.setFont(new Font("宋体", Font.BOLD, 14));
        depositButton.setBackground(Color.LIGHT_GRAY);
        depositButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        withdrawalButton.setBounds(670,270,120,40);
        withdrawalButton.setFont(new Font("宋体", Font.BOLD, 14));
        withdrawalButton.setBackground(Color.LIGHT_GRAY);
        withdrawalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        transferButton.setBounds(670,390,120,40);
        transferButton.setFont(new Font("宋体", Font.BOLD, 14));
        transferButton.setBackground(Color.LIGHT_GRAY);
        transferButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }


    protected void addElements() {
        mainPanel.add(logoLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(balanceLabelCN);
        mainPanel.add(balanceLabelEN);
        mainPanel.add(selectServerLabelCN);
        mainPanel.add(selectServerLabelEN);
        mainPanel.add(messageButton);
        mainPanel.add(exitButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawalButton);
        mainPanel.add(transferButton);

        this.add(mainPanel);
    }


    protected void addListener() {

    }


    protected void setFrameSelf() {
        this.setBounds(300,200,800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}
