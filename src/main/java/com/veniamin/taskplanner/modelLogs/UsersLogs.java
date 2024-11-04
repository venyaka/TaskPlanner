package com.veniamin.taskplanner.modelLogs;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(catalog = "Veniamin_logs", schema = "public", name = "users")
@Data
public class UsersLogs extends Logs{
}
