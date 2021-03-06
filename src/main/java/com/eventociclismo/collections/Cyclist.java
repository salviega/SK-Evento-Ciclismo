package com.eventociclismo.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cyclist {
    @Id
    private String id;
    private String teamId;
    private String name;
    private String nacionality;
    private Integer competitionNumber;

}
