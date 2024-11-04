package com.veniamin.taskplanner.repositoryLogs;

import com.veniamin.taskplanner.modelLogs.MailLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MailLogsRepository extends JpaRepository<MailLogs, UUID>, JpaSpecificationExecutor<MailLogs> {
}
