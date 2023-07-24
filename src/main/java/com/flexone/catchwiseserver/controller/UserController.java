package com.flexone.catchwiseserver.controller;

import com.flexone.catchwiseserver.domain.UserEntity;
import com.flexone.catchwiseserver.dto.UserProfileDTO;
import com.flexone.catchwiseserver.repository.UserRepository;
import com.flexone.catchwiseserver.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final JWTProvider jwtProvider;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getUserProfile(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.substring(7);
        String username = jwtProvider.getUsernameFromJwt(token);
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserProfileDTO()
                .setEmail(user.getEmail()));

    }


}
