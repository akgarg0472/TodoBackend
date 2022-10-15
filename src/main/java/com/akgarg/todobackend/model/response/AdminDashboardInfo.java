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

    @JsonProperty("total_accounts")
    private Long totalAccountsCount;

    @JsonProperty("total_users")
    private Long totalUsersCount;

    @JsonProperty("total_admins")
    private Long totalAdminsCount;

    @JsonProperty("total_todos")
    private Long totalTodosCount;

    @JsonProperty("total_active_accounts")
    private Long activeAccountsCount;

    @JsonProperty("total_blocked_accounts")
    private Long blockedAccountsCount;

    @JsonProperty("total_enabled_accounts")
    private Long enabledAccountsCount;

}
