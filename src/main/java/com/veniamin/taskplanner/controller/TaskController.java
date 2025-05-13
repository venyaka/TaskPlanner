package com.veniamin.taskplanner.controller;

import com.veniamin.taskplanner.constant.PathConstants;
import com.veniamin.taskplanner.service.AuthorizeService;
import com.veniamin.taskplanner.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.TASK_CONTROLLER_PATH)
public class TaskController {
    private final AuthorizeService authorizeService;
    private final TaskService taskService;

    @PostMapping
    public void createTask(@Valid @RequestBody CreateTaskReqDTO createTaskDTO, HttpServletRequest request){

    }
}
