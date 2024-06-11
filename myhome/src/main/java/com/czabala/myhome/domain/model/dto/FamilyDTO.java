package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.Family;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDTO {
    private Long id;
    private String name;
    private String description;
    private long userId;

    public Family toFamily() {
        return new ModelMapper().map(this, Family.class);
    }
}
