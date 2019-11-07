package com.codewalnut.demo.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 注解方式的AOP
 *
 * @author KelvinZ
 * @date 2019-11-06 19:14
 */
@Aspect
@Component
@Order(10)
public class AnnotationAopAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.codewalnut.demo.aop.annotation.SampleAop)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("<<AOP Before {}>>", joinPoint);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        log.info("\tURL : {}", req.getRequestURL().toString());
        log.info("\tHTTP_METHOD : {}", req.getMethod());
        log.info("\tIP : {}", req.getRemoteAddr());
        log.info("\tCLASS_METHOD : {}", joinPoint);
        log.info("\tARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Around("annotationPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("<<AOP Around Start {}>>", joinPoint);
        Object result = joinPoint.proceed();
        log.info("<<AOP Around End {}>>", joinPoint);
        return result;
    }

    @After("annotationPointCut()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("<<AOP After {}>>", joinPoint);
    }

    @AfterReturning(value = "annotationPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("<<AOP AfterReturning {} returned [{}]>>", joinPoint, result);
    }

    @AfterThrowing(value = "annotationPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("<<AfterThrowing {} throwing {}>>", joinPoint, e);
    }

}
