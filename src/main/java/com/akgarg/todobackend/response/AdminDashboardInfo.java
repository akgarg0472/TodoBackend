package com.akgarg.todobackend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 23-09-2022
 */
@Getter
@Setter
public class AdminDashboardInfo implements Serializable {

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
