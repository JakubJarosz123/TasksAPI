package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title", "test content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(1L, task.getId());
        assertEquals("title", task.getTitle());
        assertEquals("test content", task.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "title", "test content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("title", taskDto.getTitle());
        assertEquals("test content", taskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "test", "test content");
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(List.of(task));
        //Then
        assertEquals(1, taskDtoList.size());
        assertEquals(1L, taskDtoList.get(0).getId());
        assertEquals("test", taskDtoList.get(0).getTitle());
        assertEquals("test content", taskDtoList.get(0).getContent());
    }
}
