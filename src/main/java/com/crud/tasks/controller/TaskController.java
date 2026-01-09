package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private List<TaskDto> tasks = new ArrayList<>();
    private long nextId = 1L;

    @GetMapping
    public List<TaskDto> getTasks() {
        return tasks;
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        for (TaskDto task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    @DeleteMapping(value = "{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(taskId)) {
                tasks.remove(i);
                break;
            }
        }
    }

    @PutMapping
    public TaskDto updateTask() {
        if (!tasks.isEmpty()) {
            TaskDto updatedTask = tasks.get(0);
            updatedTask.setTitle(updatedTask.getTitle() + "[EDYCJA]");
            updatedTask.setContent(updatedTask.getContent() + "[EDYCJA]");
            return updatedTask;
        }
        return null;
    }

    @PostMapping
    public void createTask() {
        TaskDto task = new TaskDto(
                nextId++,
                "Nowe zadanie" + nextId,
                "Treść zadania"
        );
        tasks.add(task);
    }
}
