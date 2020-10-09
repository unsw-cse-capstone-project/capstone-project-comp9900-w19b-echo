package com.echo.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TempController {

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage(){
        return "home";
    }

    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
}
