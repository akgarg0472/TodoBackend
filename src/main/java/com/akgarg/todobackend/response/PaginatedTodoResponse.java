package com.akgarg.todobackend.response;

import lombok.*;

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
public class PaginatedTodoResponse {

    int currentPage;
    private long totalTodos;
    private int totalPages;
    private List<TodoResponseDto> todos;

}
