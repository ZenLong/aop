package com.codewalnut.demo.aop.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模式匹配测试控制器
 *
 * @author KelvinZ
 * @date 2019-11-07 11:18
 */
@RestController
@RequestMapping("/pattern")
public class PatternMatchAopController implements BaseController {

    @GetMapping("/hello")
    public String hello(String content) {
        return "[PatternMatch AOP]: Hello" + content;
    }

    @GetMapping("/test")
    public String test(Integer number) {
        return "[PatternMatch AOP]: Hello" + number;
    }
}
