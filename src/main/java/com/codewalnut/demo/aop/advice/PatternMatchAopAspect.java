package com.codewalnut.demo.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 模式匹配方式的AOP，无需在目标类上面增加注解，符合模式的都会被切入
 *
 * @author KelvinZ
 * @date 2019-11-07 11:03
 */
@Aspect
@Component
@Order(0)
public class PatternMatchAopAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 切入点表达式:所有com.codewalnut.demo.aop.web下所有Controller的公共方法
     */
    @Pointcut("execution(public * com.codewalnut.demo.aop.web.*Controller.*(..))")
    public void executionPointCut() {
    }

    @Pointcut("within(com.codewalnut.demo.aop.web.*)")
    public void withinPointCut() {
    }

    @Pointcut("this(com.codewalnut.demo.aop.web.BaseController)")
    public void thisPointCut() {
    }

    /**
     * 环绕切入所有控制器类
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("executionPointCut()")
    public Object doAroundOnWebController(ProceedingJoinPoint joinPoint) throws Throwable {
        long bgn = System.currentTimeMillis();

        log.info("<<Execution AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("<<Execution AOP Around End {} in {} mills>>", joinPoint, end - bgn);
        return result;
    }

    @Around("withinPointCut()")
    public Object doAroundOnWithinPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        long bgn = System.currentTimeMillis();

        log.info("<<WithinPointCut AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("<<WithinPointCut AOP Around End {} in {} mills>>", joinPoint, end - bgn);
        return result;
    }

    @Around("thisPointCut()")
    public Object doAroundOnThisPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        long bgn = System.currentTimeMillis();

        log.info("<<ThisPointCut AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("<<ThisPointCut AOP Around End {} in {} mills>>", joinPoint, end - bgn);
        return result;
    }

}
