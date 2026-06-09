package com.crud.tasks.domain;

import lombok.*;

@AllArgsConstructor
@Getter
public class TaskCard {

    private String name;
    private String description;
    private String pos;
    private String listId;
}
