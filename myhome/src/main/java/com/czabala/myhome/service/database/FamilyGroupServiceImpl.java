package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.dto.FamilyGroupDTO;
import com.czabala.myhome.domain.repository.FamilyGroupRepository;
import com.czabala.myhome.util.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FamilyGroupServiceImpl implements FamilyGroupService {
    FamilyGroupRepository familyGroupRepository;

    public FamilyGroupServiceImpl(FamilyGroupRepository familyGroupRepository) {
        this.familyGroupRepository = familyGroupRepository;
    }

    @Override
    public Set<FamilyGroup> findAll() {
        return familyGroupRepository.findAll();
    }

    @Override
    public FamilyGroup findByUser(String email) {
        FamilyGroup familyGroup = familyGroupRepository.findByUser(email);
        if (familyGroup == null) {
            throw new EntityNotFoundException("Grupo Familiar no encontrado");
        }
        return familyGroup;
    }

    @Override
    public FamilyGroup findById(long id) {
        FamilyGroup familyGroup = familyGroupRepository.findById(id);
        if (familyGroup == null) {
            throw new EntityNotFoundException("Grupo Familiar no encontrado");
        }
        return familyGroup;
    }

    @Override
    public FamilyGroup add(FamilyGroupDTO familyGroupDTO) {
        FamilyGroup familyGroup = new ModelMapper().map(familyGroupDTO, FamilyGroup.class);
        return familyGroupRepository.save(familyGroup);
    }

    @Override
    public FamilyGroup update(FamilyGroupDTO familyGroupDTO) {
        if (findById(familyGroupDTO.getId()) == null) {
            throw new EntityNotFoundException("Grupo Familiar no encontrado");
        }
        FamilyGroup familyGroup = new ModelMapper().map(familyGroupDTO, FamilyGroup.class);
        return familyGroupRepository.save(familyGroup);
    }

    @Override
    public void delete(long id) {
        findById(id);
        familyGroupRepository.deleteById(id);
    }
}
