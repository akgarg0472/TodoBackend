package com.akgarg.todobackend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Akhilesh Garg
 * @since 23-09-2022
 */
@Getter
@Setter
public class AdminDashboardInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -2164752345273567367L;

    @JsonProperty("accounts_count")
    private Long totalAccountsCount;

    @JsonProperty("users_count")
    private Long totalUsersCount;

    @JsonProperty("admins_count")
    private Long totalAdminsCount;

    @JsonProperty("todos_count")
    private Long totalTodosCount;

    @JsonProperty("active_account_count")
    private Long activeAccountsCount;

}
