package com.czabala.myhome.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyGroupDTO {
    private Long id;
    private String name;
    private String description;
}
