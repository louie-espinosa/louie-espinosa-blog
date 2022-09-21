package com.codeup.springy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({"/", "/about", "/me", "/login", "/home", "/posts", "/register"})
    public String showView(){
        return "forward:/index.html";
    }

}
