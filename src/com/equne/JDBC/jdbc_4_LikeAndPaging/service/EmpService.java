package com.equne.JDBC.jdbc_4_LikeAndPaging.service;

import com.equne.JDBC.jdbc_4_LikeAndPaging.dao.EmpDao;
import com.equne.JDBC.jdbc_4_LikeAndPaging.domain.Emp;

import java.util.ArrayList;

public class EmpService {

    private EmpDao dao = new EmpDao();  // 需要调用dao查找数据

    /*
            将用户从视图层传递过来的页码转换成DAO层需要的行索引。
        * 参数：页码 Page(int)
          返回值：ArrayList<Emp>
     */
//    public ArrayList<Emp> changePageToRowIndex(int page){
//
//        // 1. 负责将page计算成rowIndex。 （page==1 rowIndex==0； page==2 rowIndex==5； page==3  rowIndex==10）
//        int rowIndex = (page-1)*5;
//        // 2. 调用dao查找数据。
//        ArrayList list = dao.selectSalDescByPaging(rowIndex);
//        // 3. dao方法的返回值作为方法的结果交给用户。
//        return list;
//        // 以上3行代码同：
//        // return dao.selectByPaging((page-1)*5);
//    }

}
