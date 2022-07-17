package com.eventociclismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CyclistDto {
    private String id;
    @NotBlank(message = "The team id is required")
    private String teamId;
    @NotBlank(message = "The name is required")
    private String name;
    @NotBlank(message = "The nacionality is required")
    private String nacionality;
    @NotNull
    @Max(999)
    @Min(1)
    private Integer competitionNumber;

}
