package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Role;
import com.flexone.catchwiseserver.domain.UserEntity;
import com.flexone.catchwiseserver.dto.LoginResponseDTO;
import com.flexone.catchwiseserver.dto.SignupDTO;
import com.flexone.catchwiseserver.repository.RoleRepository;
import com.flexone.catchwiseserver.repository.UserRepository;
import com.flexone.catchwiseserver.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;



    public LoginResponseDTO signUp(SignupDTO signupDTO) {
        Role userRole = roleRepository.findByName("USER").orElseThrow();

        UserEntity user = new UserEntity();

        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRoles(Collections.singletonList(userRole));

        return login(user);
    }

    public LoginResponseDTO login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        return login(user);
    }

    public LoginResponseDTO login(UserEntity user) {
        log.info("User found: {}", user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtProvider.generateToken(authenticationToken);
        log.info("Token generated: {}", token);
        user.setSessionToken(token);
        userRepository.save(user);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
        loginResponseDTO.setEmail(user.getEmail());
        return loginResponseDTO;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<String> fetchAllUserTokens() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().reduce(new ArrayList<>(), (acc, user) -> {
            if (user.getSessionToken() != null)  acc.add(user.getSessionToken());
            return acc;
        }, (acc1, acc2) -> {
            acc1.addAll(acc2);
            return acc1;
        });
    }

}
