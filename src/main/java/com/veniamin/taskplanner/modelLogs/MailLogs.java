package com.veniamin.taskplanner.modelLogs;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(catalog = "Veniamin_logs", schema = "public", name = "mail")
@Data
public class MailLogs extends Logs{
}
