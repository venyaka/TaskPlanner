package com.veniamin.taskplanner.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogDTO {

    public String id;
    public LocalDateTime createdAt;
    public String level;
    public String userId;
    public String message;
    public String tableName;
}
