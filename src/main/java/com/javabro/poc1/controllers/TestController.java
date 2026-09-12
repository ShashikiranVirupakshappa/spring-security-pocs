package com.javabro.poc1.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class TestController {

    @GetMapping("get")
    public String get() {
        log.debug(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "Hello World";
    }
}
