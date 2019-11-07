package com.codewalnut.demo.aop.web;

import com.codewalnut.demo.aop.annotation.SampleAop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author KelvinZ
 * @date 2019-11-05 17:25
 */
@RestController
@RequestMapping("/anno")
public class AnnotationAopController {

    @GetMapping("/hello")
    @SampleAop(name = "AccessLogging")
    public String hello(String content) {
        return "[Anootation AOP]: Hello " + content;
    }

    @GetMapping("/throw")
    @SampleAop(name = "AccessLogging")
    public void testThrowing() {
        throw new RuntimeException("I am an sample exception!");
    }

}
