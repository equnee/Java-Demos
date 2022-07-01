package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc;

import java.util.Map;

/**
 * 用来设置表数据，组成具体对象的规则。
 *  - 如果框架的使用者（DAO）希望查询结果组成具体对象，那么如何组装由使用者提供，框架只负责按照要求组装。
 *  - 如何保证使用者提出的要求符合规范？—— 只需要符合当前接口规范即可。
 */
public interface Mapper<T> {

    /**
     *  将一条表记录组成指定对象 <策略模式>
     */
    public T orm(Map<String, Object> row);
}
