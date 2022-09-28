package com.akgarg.todobackend.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Akhilesh Garg
 * @since 21-07-2022
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedTodoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -72735473573465354L;

    private Integer currentPage;
    private Long totalTodos;
    private Integer totalPages;
    private List<TodoResponseDto> todos;

    public static PaginatedTodoResponse emptyResponse() {
        return new PaginatedTodoResponse(0, 0L, 0, Collections.emptyList());
    }

}
