package com.codewalnut.demo.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 模式匹配AOP
 *
 * @author KelvinZ
 * @date 2019-11-07 11:03
 */
@Aspect
@Component
@Order(0)
public class PatternMatchAopAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Around(value = "execution(public * com.codewalnut.demo.aop.web.*Controller.*(..))")
    public Object doAroundOnApplicationMain(ProceedingJoinPoint joinPoint) throws Throwable {
        long bgn = System.currentTimeMillis();

        log.info("<<AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("<<AOP Around End {} in {} mills>>", joinPoint, end - bgn);
        return result;
    }

}
