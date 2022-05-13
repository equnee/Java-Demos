/*
   使用mvc分层实现以下SQL联合查询
    1. 查询人口数在1000到2000之间的城市所属在哪个地区
    2. 查询每个国家的城市个数按照城市个数升序排列
    3. 查询各地区城市人口平均数按照人口平均数降序排列
    4. 查询哈尔滨所在的国家的名字
    5. 查询各地区名字和人口总数
    6. 查询美国有哪些城市列出城市名
    7. 查询人口最多的城市在哪个国家
    8. 查询每个国家的人口总数
    9. 查询城市人口数为1500万的国家名字
    10. 查询各地区总人数按照人口数总量降序排列
    11. 查询人口总数超过5000的国家名称
    12. 查询人口数大于杭州的城市都有哪些
 */
package com.equne.JDBC.jdbc_5_Practice_City.Test;

import com.equne.JDBC.jdbc_5_Practice_City.Service.AreaService;
import com.equne.JDBC.jdbc_5_Practice_City.Service.CountryService;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMain {

    public static void main(String[] args) {
        AreaService areaService = new AreaService();
        CountryService countryService = new CountryService();

        // select11
        ArrayList<String> list11 = countryService.select11(3000);
        for (String value:list11){
            System.out.println(value);
        }


        // select2
//        ArrayList<HashMap<String, String>> list2 = countryService.select2();
//        for(HashMap<String, String> map:list2){
//            System.out.println(map.get("cname") + ", " + map.get("citycount"));
//        }

        // select1
//        ArrayList<String> list1 = areaService.select1();
//        for(String value:list1){
//            System.out.println(value);
//        }



    }
}
