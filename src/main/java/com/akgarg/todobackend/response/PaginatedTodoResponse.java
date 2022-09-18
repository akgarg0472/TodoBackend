package com.akgarg.todobackend.response;

import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 21-07-2022
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedTodoResponse implements Serializable {

    private static final long serialVersionUID = -72735473573465354L;

    private int currentPage;
    private long totalTodos;
    private int totalPages;
    private List<TodoResponseDto> todos;

    public static PaginatedTodoResponse emptyResponse() {
        return new PaginatedTodoResponse(0, 0, 0, Collections.emptyList());
    }

}
