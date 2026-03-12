package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    void testFetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDto = List.of(
                new TrelloBoardDto("1", "trelloBoard1", List.of())
        );
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDto);
        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("trelloBoard1", result.get(0).getName());
        verify(trelloClient).getTrelloBoards();
    }

    @Test
    void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test card", "Test description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Card1", "https://url.com");

        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("jakubjarosz2015@gmail.com");
        ArgumentCaptor<Mail> captor = ArgumentCaptor.forClass(Mail.class);
        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        //Then
        assertEquals("1", result.getId());
        assertEquals("Card1", result.getName());
        assertEquals("https://url.com", result.getShortUrl());

        verify(emailService).send(captor.capture());
        Mail mail = captor.getValue();
        assertEquals("jakubjarosz2015@gmail.com", mail.getMailTo());
        assertEquals("Tasks: New Trello Card", mail.getSubject());
        assertTrue(mail.getMessage().contains("New card: Test card has been created on your Trello account"));
    }
}
