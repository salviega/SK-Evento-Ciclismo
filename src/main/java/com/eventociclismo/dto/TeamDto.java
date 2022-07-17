package com.eventociclismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private String id;
    @NotBlank(message = "The cyclist name is required")
    private String name;
    private List<CyclistDto> cyclists;
}
