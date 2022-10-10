package com.akgarg.todobackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Akhilesh Garg
 * @since 19-09-2022
 */
@Document("configs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoConfig {

    @Id
    private String key;
    private String value;

}
