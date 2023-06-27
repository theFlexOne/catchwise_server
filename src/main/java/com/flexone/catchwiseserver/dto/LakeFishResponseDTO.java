package com.flexone.catchwiseserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class LakeFishResponseDTO {
        private String name;
        private String description;
        private String imgUrl;
}
