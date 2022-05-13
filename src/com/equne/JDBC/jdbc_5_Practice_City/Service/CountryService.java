package com.equne.JDBC.jdbc_5_Practice_City.Service;

import com.equne.JDBC.jdbc_5_Practice_City.Dao.CountryDao;

import java.util.ArrayList;
import java.util.HashMap;

public class CountryService {

        private CountryDao dao = new CountryDao();

        public ArrayList<String> select11(int sumCountrysize){
            return dao.select11(sumCountrysize);
        }

        // 简单地调用
        public ArrayList<HashMap<String,String>> select2(){
            return dao.select2();
        }

}
