package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"admin.mail=jakubjarosz2015@gmail.com"})
public class AdminConfigTest {

    @Autowired
    private AdminConfig adminConfig;

    @Test
    void shouldReturnAdminMail() {
        // Sprawdzamy, czy Spring wstrzyknął właściwość
        assertEquals("jakubjarosz2015@gmail.com", adminConfig.getAdminMail());
    }
}
