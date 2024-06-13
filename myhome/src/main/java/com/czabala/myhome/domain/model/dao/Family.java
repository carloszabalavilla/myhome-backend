package com.czabala.myhome.domain.model.dao;

import com.czabala.myhome.domain.model.dto.FamilyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.modelmapper.ModelMapper;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "family_group")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<User> users;

    public FamilyDTO toDTO() {
        return new ModelMapper().map(this, FamilyDTO.class);
    }
}