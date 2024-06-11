package com.czabala.myhome.domain.model.dao;

import com.czabala.myhome.domain.model.dto.TaskDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.sql.Time;

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
    private String color;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Time startHour;
    @Column(nullable = false)
    private Time endHour;
    @Column(nullable = false)
    private boolean isDone;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private long createdBy;
    @ManyToOne
    private User user;

    public TaskDTO toDTO() {
        return new ModelMapper().map(this, TaskDTO.class);
    }
}
