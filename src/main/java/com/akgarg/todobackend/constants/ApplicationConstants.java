package com.akgarg.todobackend.constants;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public final class ApplicationConstants {

    public static final String NULL_OR_INVALID_VALUE = "Null or invalid value provided";
    public static final String NULL_OR_INVALID_REQUEST = "Invalid request object";
    public static final String NULL_OR_INVALID_TODO_TITLE = "Invalid todo title";
    public static final String NULL_OR_INVALID_USER_ID = "Invalid todo userId";
    public static final String TODO_CREATED_SUCCESSFULLY = "Todo created successfully";
    public static final String ERROR_UPDATING_TODO = "Error updating todo";
    public static final String TODO_NOT_FOUND = "Requested todo not found";
    public static final String TODO_NOT_FOUND_FOR_PROVIDED_ID = "No todo found with provided todoId";
    public static final String ERROR_DELETING_TODO = "Error deleting todo";
    public static final String NULL_OR_INVALID_TODO_ID = "TodoId is invalid";
    public static final String NULL_OR_INVALID_TODO_DESCRIPTION = "Invalid todo description";
    public static final String NULL_OR_INVALID_TODO_COMPLETED = "Invalid todo completion flag";
    public static final String TODO_UPDATED_SUCCESSFULLY = "Todo successfully updated";
    public static final String NO_TODO_FOUND_FOR_USER = "No todos found for the user";
    public static final String REGISTRATION_SUCCESS_CONFIRM_ACCOUNT = "Registration successful. We have sent an confirm email to $email. Please click on the link to verify your account.";
    public static final String NULL_OR_EMPTY_EMAIL = "Invalid email provided";
    public static final String INVALID_EMAIL_FORMAT = "Email provided is in invalid format";
    public static final String NULL_OR_EMPTY_PASSWORD = "Invalid password";
    public static final String INVALID_PASSWORD_FORMAT = "Password provided is in invalid format";
    public static final String INVALID_USER_LAST_NAME = "Last name is invalid";
    public static final String EMAIL_ALREADY_REGISTERED = "email-id is already registered";
    public static final String TODO_STATUS_UPDATED_SUCCESSFULLY = "Todo status successfully updated";
    public static final String USER_ROLE = "ROLE_USER";
    public static final String USER_NOT_FOUND = "No user found with email provided";
    public static final String INVALID_EMAIL_OR_PASSWORD = "Email or password is invalid";
    public static final String USER_ACCOUNT_DISABLED = "Your account is disabled";
    public static final String USER_ACCOUNT_LOCKED = "Please verify your account to continue";
    public static final String EXPIRED_JWT_TOKEN = "Token expired. Please login again";
    public static final String INVALID_JWT_TOKEN = "Token is invalid";
    public static final String UNKNOWN_JWT_TOKEN = "Token is invalid or malicious";
    public static final String FORGOT_PASSWORD_EMAIL_SUCCESS = "An email has been sent to $email. Please follow instruction on email to reset your account password";
    public static final String FORGOT_PASSWORD_EMAIL_FAILURE = "Error occurred while sending forgot password email to $email. Please try again once. If problem persists then try again after sometime";

}
