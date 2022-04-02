package com.example.pds.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


//    @RequestMapping(path = "/register", method = RequestMethod.GET)
//    public String showLoginPage() {
//     return "register";
//    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

//    @GetMapping("/users/register")
//    public String register(){
//        return "register";
//    }


//    @GetMapping("/users/login")
//    public String login(){
//        return "login";
//    }
}
