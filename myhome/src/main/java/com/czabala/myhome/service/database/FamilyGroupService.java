package com.czabala.myhome.service.database;

import com.czabala.myhome.domain.model.dao.FamilyGroup;
import com.czabala.myhome.domain.model.dto.FamilyGroupDTO;

public interface FamilyGroupService extends Service<FamilyGroup, FamilyGroupDTO>{
    FamilyGroup findByUser(String email);
}
