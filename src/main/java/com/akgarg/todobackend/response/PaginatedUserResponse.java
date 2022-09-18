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
public class PaginatedUserResponse implements Serializable {

    private static final long serialVersionUID = -364893465734657L;

    private int currentPage;
    private long totalUsers;
    private int totalPages;
    private List<UserResponseDto> todos;

    public static PaginatedUserResponse emptyResponse() {
        return new PaginatedUserResponse(0, 0, 0, Collections.emptyList());
    }

}
