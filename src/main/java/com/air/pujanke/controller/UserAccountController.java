package com.air.pujanke.controller;

import com.air.pujanke.model.dto.UserAccountDeletionDto;
import com.air.pujanke.model.dto.UserFundModificationDto;
import com.air.pujanke.model.dto.UserPasswordResetDto;
import com.air.pujanke.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserService userService;
    private final SecurityContextLogoutHandler logoutHandler;

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
    public String resetPassword(Principal userPrincipal, @ModelAttribute("passwordResetDto") @Valid UserPasswordResetDto passwordResetDto,
                                RedirectAttributes ra) {
        userService.resetPassword(userPrincipal.getName(), passwordResetDto);
        ra.addFlashAttribute("success", "Password reset successfully.");
        return "redirect:/account";
    }

    @GetMapping("/delete")
    public String getDeleteAccountPage() {
        return "delete_account";
    }

    @DeleteMapping("/delete")
    public String deleteAccount(@ModelAttribute("accountDeletionDto") @Valid UserAccountDeletionDto accountDeletionDto,
                                HttpServletRequest httpRequest,
                                HttpServletResponse httpResponse,
                                Principal userPrincipal) {

        userService.deleteAccount(userPrincipal.getName(), accountDeletionDto);
        logoutHandler.logout(httpRequest, httpResponse, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/home";
    }
}
