package com.eventociclismo.dto;

import com.eventociclismo.collections.Cyclist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private String id;
    @NotBlank(message = "The cyclist name ir required")
    private String name;
    @NotBlank(message = "The cyclist list is required")
    private Set<Cyclist> cyclistList;

}
