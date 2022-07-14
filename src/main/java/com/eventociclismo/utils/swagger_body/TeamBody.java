package com.eventociclismo.utils.swagger_body;

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
public class TeamBody {

    private String name;
    private Set<Cyclist> cyclists = new HashSet<Cyclist>();
}
