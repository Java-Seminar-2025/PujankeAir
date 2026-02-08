package com.air.pujanke.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPanelController {



    @GetMapping
    public String getAdminPanelIndex() {
        return "admin";
    }
}
