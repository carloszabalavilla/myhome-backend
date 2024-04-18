package com.czabala.myhome.domain.model.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private int spendingTime;
    @Column
    private String assignedBy;
    @Column(nullable = false)
    private boolean idEditable;
    @Column
    private Timestamp deadline;
    @Column(nullable = false)
    private boolean isDone;
    @ManyToOne
    private User user;

}
