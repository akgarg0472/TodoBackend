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
public class PaginatedUserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -364893465734657L;

    private Integer currentPage;
    private Long totalUsers;
    private Integer totalPages;
    private List<UserResponseDto> todos;

    public static PaginatedUserResponse emptyResponse() {
        return new PaginatedUserResponse(0, 0L, 0, Collections.emptyList());
    }

}
