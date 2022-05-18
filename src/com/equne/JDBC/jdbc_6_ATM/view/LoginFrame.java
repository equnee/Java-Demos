package com.equne.JDBC.jdbc_6_ATM.view;

import com.equne.JDBC.jdbc_6_ATM.service.AtmService;
import com.equne.JDBC.jdbc_6_ATM.util.BaseFrame;
import com.equne.JDBC.jdbc_6_ATM.util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("all")
public class LoginFrame extends BaseFrame {

    // 构造方法 —— 单例模式
    private LoginFrame(){
        super("登录窗口");
        this.init();
    }
    private static LoginFrame loginFrame;
    public static LoginFrame getLoginFrame(){
        if(loginFrame == null){
            loginFrame = new LoginFrame();
        }
        return loginFrame;
    }

//    public LoginFrame(){
//        this.init();
//    };
//    public LoginFrame(String title){
//        super(title);
//        this.init(); // 规定启动流程
//    };


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

    // 添加一个控制注册窗口的属性
    private RegistFrame registFrame = null;

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
                // 1. 获取账号密码
                String aname = accountField.getText();
                String apassword = new String(passwordField.getPassword());
                // 2. 调用登录方法
                AtmService service = MySpring.getBean("com.equne.JDBC.jdbc_6_ATM.service.AtmService");
                String result = service.login(aname, apassword);
                if(result.equals("登录成功")){
                    LoginFrame.this.setVisible(false);
                    AtmFrame.getAtmFrame(aname); // 用于登录成功后传递信息
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "对不起，" + result);
                    accountField.setText("");
                    passwordField.setText("");
                }
            }
        });
        registButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* ⚠️：由于使用单例模式来创建registFrame对象，从注册窗口返回后，无法点击注册再次创建新对象，因此使用隐藏窗口的方式，始终使用同一个registFrame对象，无需创建新对象。
                     1. 创建一个registFram属性，在登录窗口中判断：
                     2. 当首次点击注册：registFram属性为空，此时首次创建registFrame对象，弹出一个新的注册窗口，隐藏登录窗口。
                     3. 在注册窗口中点击关闭：隐藏注册窗口（并清空输入框以便下次使用），打开隐藏的登录窗口。
                     4. 当再次点击注册：registFram属性不为空，此时已创建过对象，弹出已使用过的隐藏的注册窗口...
                 */
                LoginFrame.this.setVisible(false); // 隐藏登录窗口
                if(registFrame ==null){
                    registFrame = RegistFrame.getRegistFram(); // new新的注册窗口
                } else{
                    registFrame.setVisible(true);
                }
            }
        });
    }

    protected void setFrameSelf() {
        this.setBounds(400, 200, 500, 300); // 距离屏幕、自身大小500x300
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击关闭时，直接退出系统
        this.setResizable(false); // 固定大小，不可拖拽
        this.setVisible(true); // 最终展示
    }


}
