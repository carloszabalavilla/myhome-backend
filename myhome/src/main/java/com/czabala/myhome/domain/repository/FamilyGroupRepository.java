package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FamilyGroupRepository extends CrudRepository<FamilyGroup, Long> {
    Set<FamilyGroup> findAll();

    FamilyGroup findById(long id);

    FamilyGroup findByUser(String email);
}
