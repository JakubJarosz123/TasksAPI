package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testGetAllTasks(){
        //Given
        List<Task> tasksList = List.of(
                new Task(1L, "test1", "test 1 content"),
                new Task(2L, "test2", "test 2 content")
        );

        when(taskRepository.findAll()).thenReturn(tasksList);
        //When
        List<Task> results = dbService.getAllTasks();
        //Then
        assertEquals(2,  results.size());
        assertEquals(1L,  results.get(0).getId());
        assertEquals(2L,  results.get(1).getId());
        assertEquals("test1",  results.get(0).getTitle());
        assertEquals("test2",  results.get(1).getTitle());
        assertEquals("test 1 content",  results.get(0).getContent());
        assertEquals("test 2 content",  results.get(1).getContent());
    }

    @Test
    void testGetTaskByIdIfFound() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "test1", "test 1 content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        //When
        Task result = dbService.getTask(1L);
        //Then
        assertEquals(1L, result.getId());
        assertEquals("test1", result.getTitle());
        assertEquals("test 1 content", result.getContent());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testGetTaskByIdIfNotFound() {
        //Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        //When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));
        verify(taskRepository).findById(1L);
    }

    @Test
    void testSaveTask() {
        //Given
        Task task = new Task(1L, "test1", "test 1 content");
        when(taskRepository.save(task)).thenReturn(task);
        //When
        Task result = dbService.saveTask(task);
        //Then
        assertEquals(1L, result.getId());
        assertEquals("test1", result.getTitle());
        assertEquals("test 1 content", result.getContent());
        verify(taskRepository).save(task);
    }

    @Test
    void testDeleteTask() {
        //Given
        Long taskId = 1L;
        //When
        dbService.deleteTask(taskId);
        //Then
        verify(taskRepository).deleteById(taskId);
    }
}
