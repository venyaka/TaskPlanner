package com.veniamin.taskplanner.modelLogs;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(catalog = "Veniamin_logs", schema = "public", name = "logs")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class Logs {

    @Id
    private String id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "level")
    private String level;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "message")
    private String message;

    public Logs() {
    }
}
