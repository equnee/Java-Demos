package com.equne.JDBC.jdbc_6_ATM.view;

import com.equne.JDBC.jdbc_6_ATM.service.AtmService;
import com.equne.JDBC.jdbc_6_ATM.util.BaseFrame;
import com.equne.JDBC.jdbc_6_ATM.util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton cancelBankButton = new JButton("销户");
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
        balanceLabelCN.setText("账户余额：¥" + service.check(aname));
        balanceLabelEN.setBounds(250,240,300,40);
        balanceLabelEN.setFont(new Font("宋体", Font.BOLD, 22));
        balanceLabelEN.setHorizontalAlignment(JTextField.CENTER);
        balanceLabelEN.setText("Account Balance：¥" + service.check(aname));

        selectServerLabelCN.setBounds(320,370,300,40);
        selectServerLabelCN.setFont(new Font("宋体", Font.PLAIN, 16));
        selectServerLabelEN.setBounds(320,400,300,40);
        selectServerLabelEN.setFont(new Font("宋体", Font.PLAIN, 16));

        cancelBankButton.setBounds(10,150,120,40);
        cancelBankButton.setFont(new Font("宋体", Font.BOLD, 14));
        cancelBankButton.setBackground(Color.LIGHT_GRAY);
        cancelBankButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        mainPanel.add(cancelBankButton);
        mainPanel.add(exitButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawalButton);
        mainPanel.add(transferButton);

        this.add(mainPanel);
    }


    protected void addListener() {
        // 销户按钮
        cancelBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(AtmFrame.this, "是否确认销户？");
                if(value == 0){
                    service.cancelAccount(aname);
                    JOptionPane.showMessageDialog(AtmFrame.this, "销户成功！");
                    AtmFrame.this.setVisible(false);
                    LoginFrame.getLoginFrame().setVisible(true);
                    LoginFrame.getLoginFrame().reset();
                } else {
                    JOptionPane.showMessageDialog(AtmFrame.this, "销户失败！");
                }
            }
        });

        // 退出按钮
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(AtmFrame.this,"是否确认退出系统？");
                if(value == 0){ // 0-是  1-否  2-取消
                    System.exit(0); // 直接让系统中断
                }
            }
        });

        // 存款按钮
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog("请输入存款金额：");
                    if(value!=null && !"".equals(value)){ // value.equals("")有可能会引起空指针异常，而空字串为正常的字符串常量。
                        Float depositAmount = Float.valueOf(value);
                        if(depositAmount <= 0){
                            throw new NumberFormatException();
                        }
                        int count = service.deposit(aname, depositAmount); // 更新成功时返回1
                        if(count == 1){
                            JOptionPane.showMessageDialog(AtmFrame.this, "存款成功");
                            balanceLabelCN.setText("账户余额：¥" + service.check(aname));
                            balanceLabelEN.setText("Account Balance：¥" + service.check(aname));
                        }else{
                            JOptionPane.showMessageDialog(AtmFrame.this, "存款失败");
                        }
                    }
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this, "对不起，输入金额有误。");
                }
            }

        });

        // 取款按钮
        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入取款金额：");
                    if(value!=null && !"".equals(value)){
                        Float withdrawalAmount = Float.valueOf(value);
                        if(withdrawalAmount <= 0){
                            throw new NumberFormatException();
                        }
                        int count = service.withdraw(aname, withdrawalAmount); // 更新成功时返回1，余额不足时返回-1
                        if(count == 1){
                            JOptionPane.showMessageDialog(AtmFrame.this, "取款成功。");
                            balanceLabelCN.setText("账户余额：¥" + service.check(aname));
                            balanceLabelEN.setText("Account Balance：¥" + service.check(aname));
                        } else if(count == -1){
                            JOptionPane.showMessageDialog(AtmFrame.this, "对不起，余额不足。");
                        }
                        else{
                            JOptionPane.showMessageDialog(AtmFrame.this, "取款失败。");
                        }
                    }
                } catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this, "对不起，输入的金额有误。");
                }
            }
        });

        // 转账按钮
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String name = JOptionPane.showInputDialog(AtmFrame.this,"请输入收款账户：");
                    if(name!=null && !"".equals(name) && service.isExist(name)){
                        String value = JOptionPane.showInputDialog(AtmFrame.this,"请输入转账金额：");
                        if(value!=null && !"".equals(value)){
                            Float transferAmount = Float.valueOf(value);
                            if(transferAmount <= 0){
                                throw new NumberFormatException();
                            }
                            int count = service.transfer(aname, name, transferAmount); // 更新成功时返回1，余额不足时返回-1
                            if(count == 2){
                                JOptionPane.showMessageDialog(AtmFrame.this, "转账成功。");
                                balanceLabelCN.setText("账户余额：¥" + service.check(aname));
                                balanceLabelEN.setText("Account Balance：¥" + service.check(aname));
                            } else if(count == -1){
                                JOptionPane.showMessageDialog(AtmFrame.this, "对不起，您的余额不足。");
                            } else {
                                JOptionPane.showMessageDialog(AtmFrame.this, "转账失败。");
                            }
                        }
                    } else{
                        JOptionPane.showMessageDialog(AtmFrame.this, "对不起，输入的账户有误。");
                    }
                } catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AtmFrame.this, "对不起，输入的金额有误。");
                }
            }
        });

    }


    protected void setFrameSelf() {
        this.setBounds(300,200,800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}
