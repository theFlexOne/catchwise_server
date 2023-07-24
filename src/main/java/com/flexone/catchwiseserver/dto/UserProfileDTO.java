package com.flexone.catchwiseserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserProfileDTO {

    private String email;

}
