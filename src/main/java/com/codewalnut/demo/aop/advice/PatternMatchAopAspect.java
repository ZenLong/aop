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

    @Pointcut("execution(public * com.codewalnut.demo.aop.web.*Controller.*(..))")
    public void allControllerPointCut() {
    }

    /**
     * 环绕切入所有控制器类
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("allControllerPointCut()")
    public Object doAroundOnWebController(ProceedingJoinPoint joinPoint) throws Throwable {
        long bgn = System.currentTimeMillis();

        log.info("<<AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("<<AOP Around End {} in {} mills>>", joinPoint, end - bgn);
        return result;
    }

}
