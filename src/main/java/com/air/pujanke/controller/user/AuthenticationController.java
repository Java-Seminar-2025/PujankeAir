package com.air.pujanke.controller.user;

import com.air.pujanke.model.dto.UserRegistrationDto;
import com.air.pujanke.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") @Valid UserRegistrationDto userDto) {
        userService.createUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal principal) {
        if (principal != null)
            return "redirect:/home";

        return "login";
    }
}
