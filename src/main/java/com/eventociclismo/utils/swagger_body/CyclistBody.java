package com.eventociclismo.utils.swagger_body;

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
public class CyclistBody {

    private String name;
    private String nacionality;
    private Integer competitionNumber;
    private String teamName;
}
