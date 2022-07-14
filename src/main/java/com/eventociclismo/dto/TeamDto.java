package com.eventociclismo.dto;

import com.eventociclismo.collections.Cyclist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private String id;
    @NotBlank(message = "The cyclist name ir required")
    private String name;
    private Set<Cyclist> cyclists = new HashSet<Cyclist>();

}
