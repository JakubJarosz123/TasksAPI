package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloControllerTest {

    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    void testGetTrelloBoards() {
        //Given
        TrelloBoardDto boardOne = new TrelloBoardDto("1", "Board1", List.of());
        TrelloBoardDto boardTwo = new TrelloBoardDto("2", "Board2", List.of());
        List<TrelloBoardDto> trelloBoardDtos = List.of(boardOne, boardTwo);
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        ResponseEntity<List<TrelloBoardDto>> result = trelloController.getTrelloBoards();
        //Then
        assertNotNull(result.getBody());
        assertEquals("1", result.getBody().get(0).getId());
        assertEquals("2", result.getBody().get(1).getId());
        assertEquals("Board1", result.getBody().get(0).getName());
        assertEquals("Board2", result.getBody().get(1).getName());
        verify(trelloFacade).fetchTrelloBoards();
    }

    @Test
    void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "Card description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new  CreatedTrelloCardDto("1", "Card1", "https://url.com");

        when(trelloFacade.createCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        ResponseEntity<CreatedTrelloCardDto> result = trelloController.createdTrelloCard(trelloCardDto);
        //Then
        assertNotNull(result.getBody());
        assertEquals("1", result.getBody().getId());
        assertEquals("Card1", result.getBody().getName());
        assertEquals("https://url.com", result.getBody().getShortUrl());
        verify(trelloFacade).createCard(trelloCardDto);
    }

    @Test
    void testGetTrelloBoards_emptyList() {
        // Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        // When
        ResponseEntity<List<TrelloBoardDto>> response = trelloController.getTrelloBoards();
        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(trelloFacade, times(1)).fetchTrelloBoards();
    }
}