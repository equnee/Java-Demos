package com.equne.JDBC.jdbc_5_Practice_City.Service;

import com.equne.JDBC.jdbc_5_Practice_City.Dao.AreaDao;

import java.util.ArrayList;

public class AreaService {

    private AreaDao dao = new AreaDao();

    public ArrayList<String> select1(){
        return dao.select1(1000, 2000);
    }


}
