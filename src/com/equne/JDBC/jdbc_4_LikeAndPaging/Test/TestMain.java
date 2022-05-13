package com.equne.JDBC.jdbc_4_LikeAndPaging.Test;

import com.equne.JDBC.jdbc_4_LikeAndPaging.dao.EmpDao;
import com.equne.JDBC.jdbc_4_LikeAndPaging.domain.Emp;
import com.equne.JDBC.jdbc_4_LikeAndPaging.service.EmpService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        EmpDao dao = new EmpDao();

        // 1. 测试-LIKE模糊查询：
//        ArrayList<Emp> list1 = dao.selectForLike("A");

        // 2. 测试-分页查询：
//         ArrayList<Emp> list2 = dao.selectByPaging(0);

        // 3. 测试-假定在视图层中分页查询：
//        EmpService service = new EmpService();
//        Scanner input = new Scanner(System.in);
//        System.out.println("请输入要查询的页码：");
//        int page = input.nextInt();
//        ArrayList<Emp> list3 = service.changePageToRowIndex(page);

        // 4. 测试-联合查询emp和dept表
//        ArrayList<Emp> list4 = dao.selectAllEmpAndDept();
        // 遍历结果
//        for(Emp emp:list){
//            System.out.println(emp);
//        }

        // 5. 测试-查询计算字段
//        ArrayList<HashMap<String, Object>> list = dao.selectCountByGroup();
//        // 遍历结果
//        for(HashMap<String, Object> map:list){
//            System.out.println("{" + map.get("deptno") + "," + map.get("ct") + "}");
//        }

    }
}
