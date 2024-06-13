package com.czabala.myhome.controller;

import com.czabala.myhome.domain.model.dto.FamilyDTO;
import com.czabala.myhome.service.FamilyService;
import com.czabala.myhome.util.mapper.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * FamilyGroupController is a REST controller that handles family group related requests.
 * It provides endpoints for finding all family groups, finding a family group by id or user,
 * creating a family group, updating a family group, and deleting a family group.
 */
@RestController
@RequestMapping("/family")
public class FamilyController {
    private final FamilyService familyService;

    /**
     * Constructs a new FamilyGroupController with the specified FamilyGroupService.
     *
     * @param familyService the FamilyGroupService to be used by the FamilyGroupController
     */
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    /**
     * Handles a GET request to find all family groups.
     *
     * @return a ResponseEntity containing a Set of all FamilyGroups
     */
    @GetMapping
    public ResponseEntity<Set<FamilyDTO>> findAllFamilyGroups() {
        return ResponseEntity.ok(familyService.findAll());
    }

    /**
     * Handles a GET request to find a family group by id.
     *
     * @param id the id of the family group to find
     * @return a ResponseEntity containing the FamilyGroup with the specified id
     */
    @GetMapping("/id")
    public ResponseEntity<FamilyDTO> findFamilyGroupById(@RequestParam long id) {
        return ResponseEntity.ok(familyService.findById(id));
    }

    /**
     * Handles a POST request to create a family group.
     *
     * @param familyGroupDTO the FamilyGroupDTO containing the new family group's information
     * @return a ResponseEntity containing the created FamilyGroup
     */
    @PostMapping
    public ResponseEntity<FamilyDTO> createFamilyGroup(@RequestBody FamilyDTO familyGroupDTO) {
        return ResponseEntity.ok(familyService.add(familyGroupDTO));
    }

    /**
     * Handles a PUT request to update a family group.
     *
     * @param familyGroupDTO the FamilyGroupDTO containing the updated family group's information
     * @return a ResponseEntity containing the updated FamilyGroup
     */
    @PutMapping
    public ResponseEntity<FamilyDTO> updateFamilyGroup(@RequestBody FamilyDTO familyGroupDTO) {
        return ResponseEntity.ok(familyService.update(familyGroupDTO));
    }

    /**
     * Handles a DELETE request to delete a family group.
     *
     * @param id the id of the family group to delete
     * @return a ResponseEntity containing a message indicating that the family group has been deleted
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFamilyGroup(@RequestParam long id) {
        familyService.delete(id);
        return JsonObject.jsonMsgResponse(200, "Grupo familiar eliminado con exito");
    }
}