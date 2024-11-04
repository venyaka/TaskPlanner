package com.veniamin.taskplanner.repositoryLogs;

import com.veniamin.taskplanner.modelLogs.AuthLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AuthLogsRepository extends JpaRepository<AuthLogs, UUID>, JpaSpecificationExecutor<AuthLogs> {
}
