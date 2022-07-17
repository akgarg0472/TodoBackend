package com.akgarg.todobackend.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Getter
@Setter
@ToString
public class TodoResponseDto {

    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Long createdAt;
    private Long updatedAt;

}
