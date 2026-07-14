package com.example.lockstock.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    @GetMapping("/")
    public String product() {
        return "products";
    }
    @GetMapping("/detail")
    public String detail(
            @RequestParam("id") Long id,
            Model model
    ) {
        model.addAttribute("id", id);
        return "/product-detail";
    }
}
