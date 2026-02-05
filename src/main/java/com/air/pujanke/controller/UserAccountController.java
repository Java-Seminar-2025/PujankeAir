package com.air.pujanke.controller;

import com.air.pujanke.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserService userService;

    @GetMapping
    public String getAccountPage(Principal userPrincipal, Model model) {
        var userAccountDetails = userService.fetchUserAccountPageDetails(userPrincipal.getName());
        model.addAttribute("account", userAccountDetails);
        return "account";
    }
}
