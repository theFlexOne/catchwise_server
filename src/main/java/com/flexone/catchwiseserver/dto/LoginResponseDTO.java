package com.flexone.catchwiseserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class LoginResponseDTO {

    private final String accessToken;
    private final UserProfileDTO userProfile = new UserProfileDTO();
    private String tokenType = "Bearer";

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponseDTO setEmail(String email) {
        this.userProfile.setEmail(email);
        return this;
    }

}
