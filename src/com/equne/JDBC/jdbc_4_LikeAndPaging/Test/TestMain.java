package com.equne.JDBC.jdbc_4_LikeAndPaging.Test;

import com.equne.JDBC.jdbc_4_LikeAndPaging.dao.EmpDao;
import com.equne.JDBC.jdbc_4_LikeAndPaging.domain.Emp;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        EmpDao dao = new EmpDao();
        ArrayList<Emp> list = dao.selectForLike("A");
        for(Emp emp:list){
            System.out.println(emp);
        }
    }
}
