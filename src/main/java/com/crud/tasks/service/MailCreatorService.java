package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {

    @Autowired
    private CompanyDetails companyDetails;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://jakubjarosz123.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "See you again");
        context.setVariable("company_name", companyDetails.getName());
        context.setVariable("company_goal", companyDetails.getGoal());
        context.setVariable("company_email", companyDetails.getEmail());
        context.setVariable("company_phone", companyDetails.getPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
