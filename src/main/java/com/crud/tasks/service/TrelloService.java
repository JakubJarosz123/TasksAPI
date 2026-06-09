package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello Card";
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewTrelloCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.sendTrelloMessage(Mail.builder()
                .mailTo(adminConfig.getAdminMail())
                .subject(SUBJECT)
                .message("New card: " + trelloCardDto.getName() + " has been created on your Trello account")
                .build()));
        return newCard;
    }

    public CreatedTaskCardDto createTaskCard(final TaskCardDto taskCardDto) {
        CreatedTaskCardDto newCard = trelloClient.createNewTaskCard(taskCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.sendTaskMessage(Mail.builder()
                .mailTo(adminConfig.getAdminMail())
                .subject(SUBJECT)
                .message("New card: " + taskCardDto.getName() + " has been created on your Trello account")
                .build()));
        return newCard;
    }
}
