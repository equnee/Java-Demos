package com.equne.JDBC.jdbc_7_ConnectionPool.test_2_JDBC.jdbc.annotations;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME) // 生命周期：运行时使用（可以在反射中使用）
@Target(ElementType.METHOD) // 表示注解在方法上使用
public @interface Select {
    String value(); // value:赋值时可以省略"value="
}
