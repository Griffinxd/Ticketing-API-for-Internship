package com.internship.ticketing.controller;

import com.internship.ticketing.model.CustomUser;
import com.internship.ticketing.security.jwt.JwtUtil;
import com.internship.ticketing.service.user.CreateNewUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    private final CreateNewUserService createNewUserService;

    public UserController(CreateNewUserService createNewUserService) {
        this.createNewUserService = createNewUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createNewUser(@RequestBody CustomUser customUser){
        return createNewUserService.execute(customUser);
    }

    @RestController
    public static class LoginController {
        private final AuthenticationManager manager;

        public LoginController(AuthenticationManager manager) {
            this.manager = manager;
        }

        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@RequestBody CustomUser user){
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = manager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = JwtUtil.generateToken((User) authentication.getPrincipal());
            return ResponseEntity.ok(Map.of("token", jwtToken));
        }
    }
}
