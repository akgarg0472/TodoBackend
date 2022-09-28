package com.akgarg.todobackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Document("users")
@Getter
@Setter
public class TodoUser {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String avatar;

    private Boolean isEnabled;
    private String disabledBy;
    private String disableReason;
    private String enabledBy;
    private String enableReason;

    private Boolean isAccountNonLocked;
    private String lockedBy;
    private String lockReason;
    private String unlockedBy;
    private String unlockReason;

    private String accountVerificationToken;
    private String forgotPasswordToken;

    private Long createdAt;
    private Long lastUpdatedAt;

    // admin related fields
    private String approvedAsAdminBy;
    private Long approvedAsAdminOn;

    public String getName() {
        return this.getFirstName().isBlank() ?
                this.getLastName() :
                this.getFirstName().concat(" ").concat(this.getLastName());
    }

    @Override
    public String toString() {
        return "TodoUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
