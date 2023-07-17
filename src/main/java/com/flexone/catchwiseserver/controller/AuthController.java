package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.Role;
import com.flexone.catchwiseserver.domain.UserEntity;
import com.flexone.catchwiseserver.dto.LoginResponseDTO;
import com.flexone.catchwiseserver.dto.LoginRequestDTO;
import com.flexone.catchwiseserver.dto.SignupDTO;
import com.flexone.catchwiseserver.repository.RoleRepository;
import com.flexone.catchwiseserver.repository.UserRepository;
import com.flexone.catchwiseserver.util.JwtProvider;
import lombok.RequiredArgsConstructor;
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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

//    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserEntity user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow(() -> new RuntimeException("Username not found"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
        loginResponseDTO.setUserProfileDTO(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupDTO signupDTO) {
        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        UserEntity user = new UserEntity();
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRoles(roleRepository.findAll());

        Role userRole = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singletonList(userRole));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

}
