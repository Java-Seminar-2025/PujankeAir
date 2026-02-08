package com.air.pujanke.controller.admin;

import com.air.pujanke.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserManagementController {

    private final UserService userService;
    private final SessionRegistry sessionRegistry;

    @GetMapping
    public String getUserManagementPage(Model model) {
        model.addAttribute("users", userService.fetchUsersForManagement());
        return "user_mgmt";
    }

    @PatchMapping("/{username}")
    public String changeUserEnabledState(@PathVariable String username){
        userService.toggleAccountEnabled(username);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username){
        userService.adminDeleteAccount(username);
        sessionRegistry.getAllPrincipals().stream().filter(p ->
                p instanceof UserDetails user && user.getUsername().equals(username))
                .forEach(p -> sessionRegistry.getAllSessions(p, false)
                        .forEach(SessionInformation::expireNow));

        return "redirect:/admin/users";
    }
}
