package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.dto.FamilyGroupDTO;
import com.czabala.myhome.service.database.FamilyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FamilyGroupControllerTest {

    @InjectMocks
    private FamilyGroupController familyGroupController;

    @Mock
    private FamilyGroupService familyGroupService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllFamilyGroupsReturnsAllFamilyGroups() {
        Set<FamilyGroup> familyGroups = new HashSet<>();
        when(familyGroupService.findAll()).thenReturn(familyGroups);

        var response = familyGroupController.findAllFamilyGroups();

        assertEquals(familyGroups, response.getBody());
    }

    @Test
    public void findFamilyGroupByIdReturnsFamilyGroup() {
        FamilyGroup familyGroup = new FamilyGroup();
        when(familyGroupService.findById(1L)).thenReturn(familyGroup);

        var response = familyGroupController.findFamilyGroupById(1L);

        assertEquals(familyGroup, response.getBody());
    }

    @Test
    public void createFamilyGroupReturnsCreatedFamilyGroup() {
        FamilyGroupDTO familyGroupDTO = new FamilyGroupDTO();
        FamilyGroup familyGroup = new FamilyGroup();
        when(familyGroupService.add(familyGroupDTO)).thenReturn(familyGroup);

        var response = familyGroupController.createFamilyGroup(familyGroupDTO);

        assertEquals(familyGroup, response.getBody());
    }

    @Test
    public void updateFamilyGroupReturnsUpdatedFamilyGroup() {
        FamilyGroupDTO familyGroupDTO = new FamilyGroupDTO();
        FamilyGroup familyGroup = new FamilyGroup();
        when(familyGroupService.update(familyGroupDTO)).thenReturn(familyGroup);

        var response = familyGroupController.updateFamilyGroup(familyGroupDTO);

        assertEquals(familyGroup, response.getBody());
    }

    @Test
    public void deleteFamilyGroupDeletesFamilyGroup() {
        familyGroupController.deleteFamilyGroup(1L);
    }
}