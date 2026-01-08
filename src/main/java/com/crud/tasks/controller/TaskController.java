package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
//    private TaskMapper taskMappper;
//    private DbService dbService;

    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }


    public TaskDto getTask(Long id) {
        return new TaskDto(1L, "test title", "test_content");
    }

    public void deleteTask(Long id) {
    }

    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    public void createTask(TaskDto taskDto) {
    }
}
