package com.codewalnut.demo.aop.annotation;

import java.lang.annotation.*;

/**
 * AOP样例注解
 *
 * @author KelvinZ
 * @date 2019-11-06 18:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SampleAop {
    String name() default "";
}
