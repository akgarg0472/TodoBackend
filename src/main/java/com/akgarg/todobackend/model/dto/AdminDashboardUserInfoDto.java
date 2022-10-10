package com.akgarg.todobackend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 24-09-2022
 */
@Getter
@Setter
public class AdminDashboardUserInfoDto {

    private String role;
    private boolean isAccountNonLocked;
    private boolean isEnabled;

}
