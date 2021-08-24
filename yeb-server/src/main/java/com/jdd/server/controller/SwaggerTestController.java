package com.jdd.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author zhoubin
 * @since 1.0.0
 */
@RestController
public class SwaggerTestController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}

