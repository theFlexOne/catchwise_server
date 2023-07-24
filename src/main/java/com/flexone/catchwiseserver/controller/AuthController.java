package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.Role;
import com.flexone.catchwiseserver.domain.UserEntity;
import com.flexone.catchwiseserver.dto.LoginResponseDTO;
import com.flexone.catchwiseserver.dto.LoginRequestDTO;
import com.flexone.catchwiseserver.dto.SignupDTO;
import com.flexone.catchwiseserver.repository.RoleRepository;
import com.flexone.catchwiseserver.repository.UserRepository;
import com.flexone.catchwiseserver.security.JWTProvider;
import com.flexone.catchwiseserver.service.UserDetailsServiceImpl;
import com.flexone.catchwiseserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    final private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupDTO signupDTO) {
        if (userService.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        if (userService.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        try {
            LoginResponseDTO responseDTO = userService.signUp(signupDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            log.error("Error while signing up", e);
            return ResponseEntity.badRequest().body("Error while signing up");
        }
    }

}
