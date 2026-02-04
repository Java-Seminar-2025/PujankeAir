package com.air.pujanke.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping
@Controller
@AllArgsConstructor
public class PublicController {

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/about-us")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/search")
    public String getSearchPage() {
        return "search";
    }
}
