//package com.veniamin.taskplanner.repositoryLogs;
//
//import com.veniamin.taskplanner.modelLogs.Logs;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.UUID;
//
//public interface LogsRepository extends JpaRepository<Logs, UUID>, JpaSpecificationExecutor<Logs> {
//
//    @Query("SELECT l FROM Logs l WHERE TYPE(l) = :type")
//    Page<Logs> findByType(@Param("type") Class<? extends Logs> type, Pageable pageable);
//}
