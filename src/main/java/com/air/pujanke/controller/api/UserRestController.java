package com.air.pujanke.controller.api;

import com.air.pujanke.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        return ResponseEntity.ok(userService.isUsernameTaken(username));
    }
}
