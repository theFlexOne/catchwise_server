package com.flexone.catchwiseserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello World!";
    }


}
