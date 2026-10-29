package com.czabala.myhome.service;

import com.czabala.myhome.domain.model.dao.Family;
import com.czabala.myhome.domain.model.dao.User;
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
    @org.springframework.transaction.annotation.Transactional
    public FamilyDTO add(FamilyDTO familyDTO) {
        Family family = familyRepository.save(familyDTO.toFamily());
        if (familyDTO.getUserId() > 0) {
            User owner = userRepository.findById(familyDTO.getUserId()).orElseThrow();
            owner.setFamily(family);
            owner.setFamilyRole(User.FamilyRole.HEAD);
            userRepository.save(owner);
        }
        return family.toDTO();

    }

    @Override
    public FamilyDTO update(FamilyDTO familyGroupDTO) {
        return familyRepository.save(familyGroupDTO.toFamily()).toDTO();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void delete(long id) {
        Family family = familyRepository.findById(id).orElseThrow();
        for (User member : family.getUsers()) {
            member.setFamily(null);
            member.setFamilyRole(null);
            userRepository.save(member);
        }
        familyRepository.deleteById(id);
    }

    private Set<FamilyDTO> toFamiliesDTO(Iterable<Family> family) {
        return StreamSupport.stream(family.spliterator(), false)
                .map(Family::toDTO)
                .collect(Collectors.toSet());
    }
}
