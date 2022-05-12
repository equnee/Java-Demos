/*
    JDBC操作
 */
package com.equne.JDBC.jdbc_4_LikeAndPaging.dao;

import com.equne.JDBC.jdbc_4_LikeAndPaging.domain.Emp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmpDao {
    String classname = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/demos";
    String user = "equne";
    String password = "1234";

    /*
        模糊查询
     */
    public ArrayList<Emp> selectForLike(String letter){
        ArrayList<Emp> list = new ArrayList<>();
        // 方式一：
        // String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE ename LIKE ?";
        // 方式二：
        String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE ename LIKE \"%\"?\"%\"";

        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            // 方式一：
            // pstat.setString(1, "%" + letter + "%");   // LIKE '%letter%'
            // 方式二：
            pstat.setString(1, letter);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()){
                Emp emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getDate("hiredate"));
                emp.setSal(rs.getFloat("sal"));
                emp.setComm(rs.getFloat("comm"));
                emp.setDeptno(rs.getInt("deptno"));
                list.add(emp); // 循环设置多行记录
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    /*
        selectAll：查询多条记录
     */
    public ArrayList<Emp> selectAll() {
        String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp";
        ArrayList<Emp> list = new ArrayList<>();
        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Emp emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getDate("hiredate"));
                emp.setSal(rs.getFloat("sal"));
                emp.setComm(rs.getFloat("comm"));
                emp.setDeptno(rs.getInt("deptno"));
                list.add(emp); // 循环设置多行记录
            }
            rs.close();
            pstat.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /*
        SelectOne: 查询单条记录
     */
    public Emp SelectOne(Integer empno){
        Emp emp = null;
        String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE empno = ?";
        try {
            Class.forName(classname);
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setInt(1, empno);
            ResultSet rs = pstat.executeQuery();
            if(rs.next()){
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getDate("hiredate"));
                emp.setSal(rs.getFloat("sal"));
                emp.setComm(rs.getFloat("comm"));
                emp.setDeptno(rs.getInt("deptno"));
            }
            rs.close();
            conn.close();
            pstat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }



}
