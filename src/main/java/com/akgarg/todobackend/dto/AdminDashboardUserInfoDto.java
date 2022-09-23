package com.akgarg.todobackend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 24-09-2022
 */
@Getter
@Setter
public class AdminDashboardUserInfoDto {

    private String role;
    private Boolean isAccountNonLocked;
    private Boolean isEnabled;

}
