package com.akgarg.todobackend.constants;

/**
 * Author: Akhilesh Garg
 * GitHub:
 * <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public final class ApplicationConstants {

    public static final String NULL_OR_INVALID_VALUE = "Null or invalid value provided";
    public static final String NULL_OR_INVALID_REQUEST = "Invalid request object";
    public static final String NULL_OR_INVALID_TODO_TITLE = "Invalid todo title";
    public static final String NULL_OR_INVALID_USER_ID = "Invalid userId";
    public static final String TODO_CREATED_SUCCESSFULLY = "Todo created successfully";
    public static final String ERROR_UPDATING_TODO = "Error updating todo";
    public static final String TODO_NOT_FOUND = "Requested todo not found";
    public static final String TODO_NOT_FOUND_FOR_PROVIDED_ID = "No todo found with provided todoId";
    public static final String ERROR_DELETING_TODO = "Error deleting todo";
    public static final String NULL_OR_INVALID_TODO_ID = "TodoId is invalid";
    public static final String NULL_OR_INVALID_TODO_DESCRIPTION = "Invalid todo description";
    public static final String NULL_OR_INVALID_TODO_COMPLETED = "Invalid todo completion flag";
    public static final String TODO_UPDATED_SUCCESSFULLY = "Todo successfully updated";
    public static final String TODO_STATUS_UPDATED_SUCCESSFULLY = "Todo status successfully updated";
    public static final String NO_TODO_FOUND_FOR_USER = "No todos found for the user";
    public static final String REGISTRATION_SUCCESS_CONFIRM_ACCOUNT = "Please verify your account by clicking on the verification email sent to your email $email";
    public static final String NULL_OR_EMPTY_EMAIL = "Invalid email provided";
    public static final String INVALID_EMAIL_FORMAT = "Email provided is in invalid format";
    public static final String NULL_OR_EMPTY_PASSWORD = "Invalid password";
    public static final String INVALID_PASSWORD_FORMAT = "Password provided is in invalid format";
    public static final String INVALID_USER_LAST_NAME = "Last name is invalid";
    public static final String EMAIL_ALREADY_REGISTERED = "Email-id is already registered";
    public static final String USER_ROLE = "ROLE_USER";
    public static final String USER_NOT_FOUND_BY_EMAIL = "No account found with email";
    public static final String USER_NOT_FOUND_BY_ID = "No user found with userId provided";
    public static final String ACCOUNT_NOT_FOUND_BY_TOKEN = "No account found with token provided";
    public static final String INVALID_EMAIL_OR_PASSWORD = "Invalid Login Credentials";
    public static final String USER_ACCOUNT_DISABLED = "Your account is disabled";
    public static final String USER_ACCOUNT_LOCKED = "Please verify your account to continue";
    public static final String EXPIRED_JWT_TOKEN = "Token expired. Please login again";
    public static final String INVALID_JWT_TOKEN = "Token is invalid";
    public static final String UNKNOWN_JWT_TOKEN = "Token is invalid or malicious or expired";
    public static final String INVALID_AUTH_TOKEN = "Authentication token is invalid";
    public static final String FORGOT_PASSWORD_EMAIL_SUCCESS = "Forgot password email sent to $email. Please follow instruction to reset your account password";
    public static final String FORGOT_PASSWORD_EMAIL_FAILURE = "Error occurred while sending forgot password email to $email. Please try again once. If problem persists then try again after sometime";
    public static final String ACCOUNT_VERIFICATION_EMAIL_SUBJECT = "Verify Account";
    public static final String ACCOUNT_VERIFICATION_SUCCESS_EMAIL_SUBJECT = "Congratulations 🎉, Account activated successfully";
    public static final String ACCOUNT_VERIFIED_SUCCESSFUL = "Account $EMAIL verified successfully";
    public static final String ACCOUNT_VERIFICATION_FAILED = "No verification token provided. Account verification failed";
    public static final String ACCOUNT_VERIFICATION_TOKEN_INVALID = "Verification token is invalid or expired. Account verification failed";
    public static final String PASSWORD_RESET_SUCCESS = "Password change successfully";
    public static final String PASSWORD_RESET_FAILED = "Forgot Token provided is invalid or expired";
    public static final String INVALID_FORGOT_PASSWORD_REQUEST = "Invalid forgot password request";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY_SUBJECT = "Password changed successfully";
    public static final String INVALID_TOKEN_BIT_PROVIDED = "Last unit does not have enough valid bits";
    public static final String PROFILE_UPDATED_SUCCESSFULLY = "Profile updated successfully";
    public static final String PROFILE_UPDATE_FAILED = "Failed to update profile";
    public static final String REDUNDANT_PROFILE_UPDATE_REQUEST = "Redundant profile update request";
    public static final String INVALID_OLD_PASSWORD = "Old password is incorrect";
    public static final String PASSWORDS_MISMATCHED = "Passwords didn't matched";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully";
    public static final String ERROR_CHANGING_PASSWORD = "Error changing password";
    public static final String INVALID_PASSWORD_CHANGE_REQUEST = "Invalid password change request";
    public static final String USER_PROFILE_DELETED_SUCCESSFULLY = "Account deleted successfully";
    public static final String USER_NOT_FOUND_BY_EMAIL_AND_ID = "No user found with provided userId & email";

    public static final String LOGIN_SUCCESS_RESPONSE_TOKEN = "authToken";
    public static final String LOGIN_SUCCESS_RESPONSE_ROLE = "role";
    public static final String LOGIN_SUCCESS_RESPONSE_USERID = "userId";
    public static final String LOGIN_SUCCESS_RESPONSE_EMAIL = "emailId";
    public static final String LOGIN_SUCCESS_RESPONSE_NAME = "name";

    public static final String LOGOUT_REQUEST_TOKEN = "authToken";
    public static final String INVALID_LOGOUT_REQUEST = "Invalid logout request";
    public static final String INVALID_FORGOT_PASSWORD_TOKEN = "Invalid forgot password token";

    private ApplicationConstants() {
    }

}
