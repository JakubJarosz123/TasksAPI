package com.crud.tasks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalHttpErrorHandlerTest {

    private final GlobalHttpErrorHandler globalHttpErrorHandler = new GlobalHttpErrorHandler();

    @Test
    void testHandleTaskNotFound() {
        //Given
        TaskNotFoundException exception = new TaskNotFoundException();
        //When
        ResponseEntity<Object> result = globalHttpErrorHandler.handleTaskNotFound(exception);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Task with given id doesn't exist", result.getBody());
    }
}
