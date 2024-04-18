package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private long id;
    private String name;
    private String description;
    private int spendingTime;
    private String assignedBy;
    private boolean idEditable;
    private Timestamp deadline;
    private boolean isDone;
    private User user;

}
