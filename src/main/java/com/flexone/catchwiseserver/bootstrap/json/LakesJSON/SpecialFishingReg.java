
package com.flexone.catchwiseserver.bootstrap.json.LakesJSON;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialFishingReg {

    private Long locDisplayType;
    private String location;
    private List<Reg> regs;
}
