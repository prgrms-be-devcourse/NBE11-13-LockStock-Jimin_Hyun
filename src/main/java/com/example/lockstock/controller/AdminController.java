package com.example.lockstock.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }

    @GetMapping("/write")
    public String write() {
        return "/product-write";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "product-update";
    }
}
