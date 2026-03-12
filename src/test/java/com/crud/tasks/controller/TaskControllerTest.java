package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;


    @Test
    void testGetTasks() {
        //Given
        List<Task> tasks = List.of(new Task(1L, "task1", "test 1 content"));
        List<TaskDto> taskDto =List.of(new TaskDto(1L, "task1", "test 1 content"));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDto);
        //When
        ResponseEntity<List<TaskDto>> result = taskController.getTasks();
        //Then
        assertEquals(1, result.getBody().size());
        assertEquals(1L, result.getBody().get(0).getId());
        assertEquals("task1", result.getBody().get(0).getTitle());
        assertEquals("test 1 content", result.getBody().get(0).getContent());
    }

    @Test
    void testGetTaskById() throws TaskNotFoundException {
        //Given
        Long taskId = 1L;
        Task task = new Task(taskId, "task1", "test 1 content");
        TaskDto taskDto = new TaskDto(taskId, "task1", "test 1 content");

        when(dbService.getTask(taskId)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When
        ResponseEntity<TaskDto> result = taskController.getTask(taskId);
        //Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(taskId, result.getBody().getId());
        assertEquals("task1", result.getBody().getTitle());
        assertEquals("test 1 content", result.getBody().getContent());
        verify(dbService).getTask(taskId);
        verify(taskMapper).mapToTaskDto(task);
    }

    @Test
    void testDeleteTask() {
        //Given
        Long taskId = 1L;
        //When
        ResponseEntity<Void> result = taskController.deleteTask(1L);
        //Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode ());
        verify(dbService).deleteTask(taskId);
    }

    @Test
    void testUpdateTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "task1", "test 1 content");
        Task mappedTask = new Task(1L, "task1", "test 1 content");
        Task savedTask = new Task(1L, "task2", "test 2 content");
        TaskDto updatedTaskDto = new TaskDto(1L, "task2", "test 2 content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(mappedTask);
        when(dbService.saveTask(mappedTask)).thenReturn(savedTask);
        when(taskMapper.mapToTaskDto(savedTask)).thenReturn(updatedTaskDto);
        //When
        ResponseEntity<TaskDto> result = taskController.updateTask(taskDto);
        //Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals("task2", result.getBody().getTitle());
        assertEquals("test 2 content", result.getBody().getContent());
        verify(taskMapper).mapToTaskDto(savedTask);
        verify(taskMapper).mapToTask(taskDto);
        verify(dbService).saveTask(mappedTask);
    }

    @Test
    void testCreateTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "task1", "test 1 content");
        Task task = new Task(1L, "task1", "test 1 content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        //When
        ResponseEntity<Void> result = taskController.createTask(taskDto);
        //Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(taskMapper).mapToTask(taskDto);
        verify(dbService).saveTask(task);
    }
}