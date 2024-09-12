package com.example.test_security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String AdminP(){
        log.info("admin page connect");
        return "admin";
    }
}
