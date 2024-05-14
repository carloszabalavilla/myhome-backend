package com.czabala.myhome.domain.model.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "family_group")
public class FamilyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @OneToMany(mappedBy = "familyGroup", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<User> users;

}