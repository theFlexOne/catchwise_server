package com.flexone.catchwiseserver.service;

import com.flexone.catchwiseserver.domain.Role;
import com.flexone.catchwiseserver.domain.UserEntity;
import com.flexone.catchwiseserver.dto.LoginResponseDTO;
import com.flexone.catchwiseserver.dto.SignupDTO;
import com.flexone.catchwiseserver.repository.RoleRepository;
import com.flexone.catchwiseserver.repository.UserRepository;
import com.flexone.catchwiseserver.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;



    public void signUp(SignupDTO signupDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setRoles(roleRepository.findAll());

        Role userRole = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singletonList(userRole));

        userRepository.save(user);
    }

    public LoginResponseDTO login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
        loginResponseDTO.setUserProfileDTO(user.getUsername(), user.getEmail());
        return loginResponseDTO;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
