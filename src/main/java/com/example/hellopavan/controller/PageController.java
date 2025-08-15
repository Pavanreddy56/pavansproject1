package com.example.hellopavan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hello — this is pavanreddy");
        model.addAttribute("message", "hello this is pavanreddy");
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home — welcome pavanreddy");
        model.addAttribute("message", "hy pavan reddy welcome to home");
        return "home";
    }

    @GetMapping("/will")
    public String will(Model model) {
        model.addAttribute("title", "Will — next project");
        model.addAttribute("message", "pavanreddy this is you are next project");
        return "will";
    }
}