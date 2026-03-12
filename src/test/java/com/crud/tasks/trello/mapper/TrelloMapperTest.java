package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloMapperTest {

    private final TrelloMapper mapper = new TrelloMapper();

    @Test
    void shouldMapToBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "List1", true);
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "Board1", List.of(trelloListDto));
        //When
        List<TrelloBoard> trelloBoard = mapper.mapToBoards(List.of(boardDto));
        //Then
        assertEquals(1, trelloBoard.size());
        assertEquals("1", trelloBoard.get(0).getId());
        assertEquals("Board1", trelloBoard.get(0).getName());
        assertEquals(1, trelloBoard.get(0).getLists().size());
        assertEquals("1", trelloBoard.get(0).getLists().get(0).getId());
        assertEquals("List1", trelloBoard.get(0).getLists().get(0).getName());
    }

    @Test
    void shouldMapToBoardsDto() {
        //Given
        TrelloList trelloList = new TrelloList("1", "List1", true);
        TrelloBoard trelloBoard = new TrelloBoard("1", "Board1", List.of(trelloList));
        //When
        List<TrelloBoardDto> trelloBoardDto = mapper.mapToBoardsDto(List.of(trelloBoard));
        //Then
        assertEquals(1, trelloBoardDto.size());
        assertEquals("1", trelloBoardDto.get(0).getId());
        assertEquals("Board1", trelloBoardDto.get(0).getName());
        assertEquals(1, trelloBoardDto.get(0).getLists().size());
        assertEquals("1", trelloBoardDto.get(0).getLists().get(0).getId());
        assertEquals("List1", trelloBoardDto.get(0).getLists().get(0).getName());
    }

    @Test
    void shouldMapToList() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("2", "List2", false);
        //When
        List<TrelloList> trelloList = mapper.mapToList(List.of(trelloListDto));
        //Then
        assertEquals(1, trelloList.size());
        assertEquals("2", trelloList.get(0).getId());
        assertEquals("List2", trelloList.get(0).getName());
        assertFalse(trelloList.get(0).isClosed());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("2", "List2", false);
        //When
        List<TrelloListDto> trelloListDto = mapper.mapToListDto(List.of(trelloList));
        //Then
        assertEquals(1, trelloListDto.size());
        assertEquals("2", trelloListDto.get(0).getId());
        assertEquals("List2", trelloListDto.get(0).getName());
        assertFalse(trelloListDto.get(0).isClosed());
    }

    @Test
    void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "Card nr 1", "top", "1");
        //When
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Card1", trelloCard.getName());
        assertEquals("Card nr 1", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card1", "Card nr 1", "top", "1");
        //When
        TrelloCardDto trelloCardDto = mapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Card1", trelloCardDto.getName());
        assertEquals("Card nr 1", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }
}
