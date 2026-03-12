package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void testSendInformationEmail() {
        //Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("jakubjarosz2015@gmail.com");

        ArgumentCaptor<Mail> captor = ArgumentCaptor.forClass(Mail.class);
        //When
        emailScheduler.sendInformationEmail();
        //Then
        verify(simpleEmailService).send(captor.capture());
        Mail mail = captor.getValue();
        assertEquals("Currently in database you got: 1Task", mail.getMessage());
    }
}
