package com.czabala.myhome.domain.model.dto;

import com.czabala.myhome.domain.model.dao.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class TaskDTO {
    @Setter
    @JsonProperty("id")
    private long id;
    @Setter
    @JsonProperty("name")
    private String name;
    @Setter
    @JsonProperty("description")
    private String description;
    @Setter
    @JsonProperty("color")
    private String color;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("startHour")
    private Time startHour;
    @JsonProperty("endHour")
    private Time endHour;
    @Setter
    @JsonProperty("isDone")
    private boolean isDone;
    @JsonProperty("createdAt")
    private Date createdAt;
    @Setter
    @JsonProperty("createdBy")
    private long createdBy;
    @Setter
    @JsonProperty("user")
    private long userId;

    public TaskDTO(long id, String name, String description, String date, String startHour, String endHour, boolean isDone, String createdAt, long createdBy, long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        setDate(date);
        setStartHour(startHour);
        setEndHour(endHour);
        this.isDone = isDone;
        setCreatedAt(createdAt);
        this.createdBy = createdBy;
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = Date.valueOf(ZonedDateTime.parse(date).toLocalDate());
    }

    public void setStartHour(String startHour) {
        this.startHour = Time.valueOf(ZonedDateTime.parse(startHour).toLocalTime());
    }

    public void setEndHour(String endHour) {
        this.endHour = Time.valueOf(ZonedDateTime.parse(endHour).toLocalTime());
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = Date.valueOf(ZonedDateTime.parse(createdAt).toLocalDate());
    }

    public Task toTask() {
        return new ModelMapper().map(this, Task.class);
    }
}
