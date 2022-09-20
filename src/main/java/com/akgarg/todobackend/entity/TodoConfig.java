package com.akgarg.todobackend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-09-2022
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
