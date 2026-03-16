package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DbService service;

    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyTasks() throws Exception {
        //Given
        when(service.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(List.of())).thenReturn(List.of());
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchAllTasks() throws Exception {
        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        when(service.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.mapToTaskDtoList(List.of(task))).thenReturn(List.of(taskDto));
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test content")));
    }

    @Test
    void shouldFetchTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        when(service.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(5L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(5L, "Test", "Test content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");
        Task task = new Task(1L, "Test", "Test content");

        Task savedTask = new Task(1L, "Test 1", "Test 1 content");
        TaskDto updatedTaskDto = new TaskDto(1L, "Test 1", "Test 1 content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(savedTask);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test 1 content")));
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        //Given
        Long taskId = 1L;
        Task task = new Task(1L, "Test", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test content");

        doNothing().when(service).deleteTask(taskId);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }
}