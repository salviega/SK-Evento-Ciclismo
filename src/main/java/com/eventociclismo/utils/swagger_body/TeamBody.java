package com.eventociclismo.utils.swagger_body;

import com.eventociclismo.dto.CyclistDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamBody {

    private String name;
    private List<CyclistDto> cyclists;}
