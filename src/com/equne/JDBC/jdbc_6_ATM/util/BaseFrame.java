package com.equne.JDBC.jdbc_6_ATM.util;

import javax.swing.*;
import java.awt.*;
// 参考之前的考试系统

public abstract class BaseFrame extends JFrame {
    // 模版模式

    // 构造方法
    public BaseFrame(){} // 供子类用的
    public BaseFrame(String title){ // 用作展示
        super(title);
    }

    // 设计一个具体的方法：规定加载窗体时的执行顺序。
    protected void init(){
        this.setFontAndSoOn();
        this.addElements();
        this.addListener();
        this.setFrameSelf();
    }

    /* 设计了一个通用的画图方法：提供图片路径及宽高
        - 封装方法：绘制logo
        * 参数：图片路径、图片尺寸
        * 返回值：icon对象
     */


    protected ImageIcon drawImage(String path, int width, int height){
        // 1. 通过给定的路径创建一个icon对象
        ImageIcon imageIcon = new ImageIcon(path);
        // 2. 设置icon对象内的 image 对象信息
        // imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT); // get拿出对象，修改属性内容。 ——> set回去
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        // 3. 将icon对象返回
        return imageIcon;
    }



    /*  抽象方法，只规定该做什么，有顺序执行。以后子类具体实现
     */
    // 1. 用来设置字体、颜色、背景、布局管理……
    protected abstract void setFontAndSoOn();
    // 2. 用来添加所有组件
    protected  abstract void addElements();
    // 3. 用来添加事件监听器
    protected abstract void addListener();
    // 4. 设置窗体自身的属性
    protected abstract void setFrameSelf();
}
