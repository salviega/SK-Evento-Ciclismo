package com.eventociclismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CyclistDto {
    @NotBlank(message = "The nacionality is required")
    private String nacionality;
    @NotBlank(message = "The competition is required")
    private int competitionNumber;
    @NotBlank(message = "The team name is required")
    private String teamName;
}
