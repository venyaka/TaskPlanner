package com.veniamin.taskplanner.repositoryLogs;

import com.veniamin.taskplanner.modelLogs.UsersLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UsersLogsRepository extends JpaRepository<UsersLogs, UUID>, JpaSpecificationExecutor<UsersLogs> {
}
