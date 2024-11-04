//package com.veniamin.taskplanner.service;
//
//import com.veniamin.taskplanner.dto.LogDTO;
//import com.veniamin.taskplanner.modelLogs.Logs;
//import com.veniamin.taskplanner.modelLogs.LogsType;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.io.IOException;
//
//public interface LogsService {
//
//    Page<LogDTO> getLogs(Specification<Logs> specification, Pageable pageable);
//
//    Page<LogDTO> getLogsByType(LogsType type, Pageable pageable);
//
//    Page<String> getLogFilesURLs(Pageable pageable) throws IOException;
//
//    ByteArrayResource getByPath(String path);
//}
