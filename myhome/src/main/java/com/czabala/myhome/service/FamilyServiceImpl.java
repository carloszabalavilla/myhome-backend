package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.Family;
import com.czabala.myhome.domain.model.dto.FamilyDTO;
import com.czabala.myhome.domain.repository.FamilyRepository;
import com.czabala.myhome.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FamilyServiceImpl implements FamilyService {
    FamilyRepository familyRepository;
    UserService userService;
    UserRepository userRepository;

    public FamilyServiceImpl(FamilyRepository familyGroupRepository, UserService userService, UserRepository userRepository) {
        this.familyRepository = familyGroupRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Set<FamilyDTO> findAll() {
        return toFamiliesDTO(familyRepository.findAll());
    }

    @Override
    public FamilyDTO findById(long id) {
        return familyRepository.findById(id).orElseThrow().toDTO();
    }

    @Override
    public FamilyDTO add(FamilyDTO familyDTO) {
        return familyRepository.save(familyDTO.toFamily()).toDTO();

    }

    @Override
    public FamilyDTO update(FamilyDTO familyGroupDTO) {
        return familyRepository.save(familyGroupDTO.toFamily()).toDTO();
    }

    @Override
    public void delete(long id) {
        familyRepository.deleteById(id);
    }

    private Set<FamilyDTO> toFamiliesDTO(Iterable<Family> family) {
        return StreamSupport.stream(family.spliterator(), false)
                .map(Family::toDTO)
                .collect(Collectors.toSet());
    }
}
