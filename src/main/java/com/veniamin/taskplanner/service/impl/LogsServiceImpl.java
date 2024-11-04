//package com.veniamin.taskplanner.service.impl;
//
//import com.veniamin.taskplanner.configuration.mapstruct.LogsToLogsDTO;
//import com.veniamin.taskplanner.dto.LogDTO;
//import com.veniamin.taskplanner.modelLogs.Logs;
//import com.veniamin.taskplanner.modelLogs.LogsType;
//import com.veniamin.taskplanner.repositoryLogs.LogsRepository;
//import com.veniamin.taskplanner.service.LogsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.util.FileCopyUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class LogsServiceImpl implements LogsService {
//
//    private final LogsRepository logsRepository;
//
//    @Value("${logging.files.path}")
//    private String path;
//    private final LogsToLogsDTO logsToLogsDTOMapper;
//
//    @Override
//    public Page<LogDTO> getLogs(Specification<Logs> specification, Pageable pageable) {
//        return logsRepository.findAll(specification, pageable)
//                .map(l -> {
//                    LogDTO logDTO = logsToLogsDTOMapper.sourceToDestination(l);
//                    logDTO.setTableName(l.getClass().getSimpleName());
//                    return logDTO;
//                });
//    }
//
//    @Override
//    public Page<LogDTO> getLogsByType(LogsType type, Pageable pageable) {
//        return logsRepository.findByType(type.type, pageable)
//                .map(l -> {
//                    LogDTO logDTO = logsToLogsDTOMapper.sourceToDestination(l);
//                    logDTO.setTableName(l.getClass().getSimpleName());
//                    return logDTO;
//                });
//    }
//
//    @Override
//    public Page<String> getLogFilesURLs(Pageable pageable) {
//        List<String> paths = listOfFiles(new File(path));
//        return new PageImpl<>(paths, pageable, paths.size());
//    }
//
//    public List<String> listOfFiles(File dirPath) {
//        List<String> fileNames = new ArrayList<>();
//        File filesList[] = dirPath.listFiles();
//        for (File file : filesList) {
//            if (file.isFile()) {
//                fileNames.add(file.getAbsolutePath());
//            } else {
//                List<String> innerFileNames = listOfFiles(file);
//                fileNames.addAll(innerFileNames);
//            }
//        }
//        return fileNames;
//    }
//
//    @Override
//    public ByteArrayResource getByPath(String path) {
//        try {
//            File f = new File(path);
//
//            if (f.exists() && f.canRead()) {
//                byte[] bytes = FileCopyUtils.copyToByteArray(f);
//                return new ByteArrayResource(bytes);
//            } else {
//                throw new RuntimeException("Could not read the file!");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//}
