package com.hzcominfo.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 统计
 * 用户点击操作（行为统计）  IoC容器
 */
@Target(ElementType.METHOD) // 目标作用在方法之上
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickBehavior {
    String value(); //注解时需要传的参数
}
