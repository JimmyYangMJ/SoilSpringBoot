package com.soil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MyControllerTest {
    @RequestMapping("/out")
    @ResponseBody
    public String out(){
        String a = "666";
        return "success";
    }
}
