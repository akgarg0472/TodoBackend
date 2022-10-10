package com.akgarg.todobackend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akhilesh Garg
 * @since 27-09-2022
 */
@Getter
@Setter
public class TodoUserDto {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String avatar;
    private boolean isEnabled;
    private String disabledBy;
    private String disableReason;
    private String enabledBy;
    private String enableReason;
    private boolean isAccountNonLocked;
    private String lockedBy;
    private String lockReason;
    private String unlockedBy;
    private String unlockReason;
    private long createdAt;
    private long lastUpdatedAt;
    private String approvedAsAdminBy;
    private long approvedAsAdminOn;

}
