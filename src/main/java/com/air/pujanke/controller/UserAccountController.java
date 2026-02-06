package com.air.pujanke.controller;

import com.air.pujanke.model.dto.UserFundModificationDto;
import com.air.pujanke.model.dto.UserPasswordResetDto;
import com.air.pujanke.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PatchMapping
    public String updateFunds(Principal userPrincipal, @Valid @ModelAttribute("fundsDto")UserFundModificationDto fundsDto) {
        userService.modifyUserFunds(userPrincipal.getName(), fundsDto);
        return "redirect:/account";
    }

    @GetMapping("/reset-password")
    public String getResetPasswordPage() {
        return "reset_password";
    }

    @PatchMapping("/reset-password")
    public String resetPassword(Principal userPrincipal, @ModelAttribute("passwordResetDto") UserPasswordResetDto passwordResetDto,
                                RedirectAttributes ra) {
        userService.resetPassword(userPrincipal.getName(), passwordResetDto);
        ra.addFlashAttribute("success", "Password reset successfully.");
        return "redirect:/account";
    }
}
