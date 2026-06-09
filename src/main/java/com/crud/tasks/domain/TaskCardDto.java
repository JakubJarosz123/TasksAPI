package com.crud.tasks.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCardDto {

    private String name;
    private String description;
    private String pos;
    private String listId;
}
