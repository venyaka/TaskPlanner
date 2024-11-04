package com.veniamin.taskplanner.configuration.mapstruct;

import com.veniamin.taskplanner.dto.LogDTO;
import com.veniamin.taskplanner.modelLogs.Logs;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LogsToLogsDTO {

    LogDTO sourceToDestination(Logs source);

}
