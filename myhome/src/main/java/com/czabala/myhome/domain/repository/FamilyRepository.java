package com.czabala.myhome.domain.repository;

import com.czabala.myhome.domain.model.dao.Family;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends CrudRepository<Family, Long> {
    Optional<Family> findById(long id);

}
