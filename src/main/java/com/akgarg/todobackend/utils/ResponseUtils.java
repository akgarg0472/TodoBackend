package com.akgarg.todobackend.utils;

import com.akgarg.todobackend.response.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 18-09-2022
 */
public class ResponseUtils {

    private static final String MESSAGE = "message";
    private static final String SUCCESS = "success";
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR_MESSAGE = "error_message";
    private static final String ERROR_STATUS = "error_status";

    private ResponseUtils() {
    }

    public static LoginResponse generateLoginSuccessRequest(final Map<String, String> loginProps) {
        final var response = new LoginResponse();

        response.setAuthToken(loginProps.get(LOGIN_SUCCESS_RESPONSE_TOKEN));
        response.setRole(loginProps.get(LOGIN_SUCCESS_RESPONSE_ROLE));
        response.setUserId(loginProps.get(LOGIN_SUCCESS_RESPONSE_USERID));
        response.setName(loginProps.get(LOGIN_SUCCESS_RESPONSE_NAME));
        response.setEmail(loginProps.get(LOGIN_SUCCESS_RESPONSE_EMAIL));
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static Map<String, Object> generateForgotPasswordResponse(final boolean emailResponse, final String email) {
        final var response = new HashMap<String, Object>();

        final String responseMessage = emailResponse ? FORGOT_PASSWORD_EMAIL_SUCCESS.replace("$email", email) : FORGOT_PASSWORD_EMAIL_FAILURE.replace("$email", email);

        response.put(MESSAGE, responseMessage);
        response.put(SUCCESS, emailResponse);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static ResponseEntity<Map<String, Object>> generateAccountVerificationFailResponse(
    ) {
        final var response = new HashMap<String, Object>();
        int responseStatusCode = 400;

        response.put(ERROR_STATUS, responseStatusCode);
        response.put(ERROR_MESSAGE, ACCOUNT_VERIFICATION_FAILED);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(responseStatusCode).body(response);
    }

    public static ResponseEntity<Map<String, Object>> generateForgotPasswordCompleteResponse(
            final boolean isRequestValid, final boolean passwordResetResponse
    ) {
        final var response = new HashMap<String, Object>();
        int statusCode;

        if (!isRequestValid) {
            statusCode = 400;
            response.put(ERROR_STATUS, statusCode);
            response.put(ERROR_MESSAGE, INVALID_FORGOT_PASSWORD_REQUEST);
        } else if (!passwordResetResponse) {
            statusCode = 400;
            response.put(ERROR_STATUS, statusCode);
            response.put(ERROR_MESSAGE, PASSWORD_RESET_FAILED);
        } else {
            statusCode = 200;
            response.put(MESSAGE, PASSWORD_RESET_SUCCESS);
            response.put(STATUS, statusCode);
        }

        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(statusCode).body(response);
    }

    public static ResponseEntity<Map<String, Object>> generateUpdateProfileResponse(final String updateResponse) {
        final var response = new HashMap<String, Object>();
        int statusCode;

        switch (updateResponse) {
            case PROFILE_UPDATED_SUCCESSFULLY:
            case PASSWORD_CHANGED_SUCCESSFULLY:
            case USER_PROFILE_DELETED_SUCCESSFULLY:
                statusCode = 200;
                response.put(MESSAGE, updateResponse);
                response.put(STATUS, statusCode);
                break;

            case REDUNDANT_PROFILE_UPDATE_REQUEST:
            case INVALID_OLD_PASSWORD:
            case PASSWORDS_MISMATCHED:
            case NULL_OR_INVALID_REQUEST:
            case INVALID_PASSWORD_CHANGE_REQUEST:
                statusCode = 400;
                response.put(ERROR_MESSAGE, updateResponse);
                response.put(ERROR_STATUS, statusCode);
                break;

            default:
                statusCode = 500;
                response.put(ERROR_MESSAGE, updateResponse);
                response.put(ERROR_STATUS, statusCode);
                break;
        }

        return ResponseEntity.status(statusCode).body(response);
    }

    public static SignupResponse generateSignupResponse(final String message, final int status) {
        final var signupResponse = new SignupResponse();

        signupResponse.setMessage(message);
        signupResponse.setStatus(status);
        signupResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return signupResponse;
    }

    public static ResponseEntity<Map<String, Object>> generateGetProfileResponse(final UserResponseDto userProfile) {
        final var response = new HashMap<String, Object>();
        int statusCode = userProfile != null ? 200 : 404;

        if (statusCode == 200) {
            response.put(STATUS, statusCode);
        } else {
            response.put(ERROR_STATUS, statusCode);
        }

        response.put("user", userProfile);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.status(statusCode).body(response);
    }

    public static Map<String, Object> generateGetAllUsersResponse(final PaginatedUserResponse users) {
        final var response = new HashMap<String, Object>();

        response.put("users", users);
        response.put(SUCCESS, true);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static ResponseEntity<Map<String, Object>> generateAdminDashboardResponse(final AdminDashboardInfo adminDashboard) {
        final var response = new HashMap<String, Object>();

        response.put("data", adminDashboard);
        response.put(SUCCESS, true);
        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> generateChangeAccountTypeResponse(final boolean changeAccountTypeResponse) {
        final var response = new HashMap<String, Object>();
        int responseStatusCode;

        if (changeAccountTypeResponse) {
            response.put(MESSAGE, ACCOUNT_TYPE_UPDATED_SUCCESS);
            responseStatusCode = 200;
            response.put(STATUS, responseStatusCode);
        } else {
            response.put(ERROR_MESSAGE, ERROR_UPDATING_ACC_TYPE);
            responseStatusCode = 500;
            response.put(ERROR_STATUS, responseStatusCode);
        }

        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());
        response.put(SUCCESS, changeAccountTypeResponse);

        return ResponseEntity.status(responseStatusCode).body(response);
    }

    public static ResponseEntity<Map<String, Object>> generateAccountLockStateChangeResponse(final boolean lockStateChangeResponse) {
        return getAccountStateChangeResponse(lockStateChangeResponse, ACCOUNT_LOCK_STATE_UPDATED_SUCCESS, ACCOUNT_LOCK_STATE_UPDATED_FAILED);
    }

    public static ResponseEntity<Map<String, Object>> generateAccountEnabledStateChangeResponse(final boolean enableStateChangeResponse) {
        return getAccountStateChangeResponse(enableStateChangeResponse, ACCOUNT_ENABLED_STATE_CHANGE_SUCCESS, ACCOUNT_ENABLED_STATE_CHANGE_FAILED);
    }

    private static ResponseEntity<Map<String, Object>> getAccountStateChangeResponse(
            boolean state, String successMessage, String errorMessage
    ) {
        final var response = new HashMap<String, Object>();
        int responseStatusCode;

        if (state) {
            response.put(MESSAGE, successMessage);
            responseStatusCode = 200;
            response.put(STATUS, responseStatusCode);
        } else {
            response.put(ERROR_MESSAGE, errorMessage);
            responseStatusCode = 200;
            response.put(ERROR_STATUS, responseStatusCode);
        }

        response.put(TIMESTAMP, DateTimeUtils.getCurrentDateTimeInMilliseconds());
        response.put(SUCCESS, state);

        return ResponseEntity.status(responseStatusCode).body(response);
    }

    public static TodoApiResponse generateTodoApiResponse(final String message, final Object data, final int status) {
        final var response = new TodoApiResponse();

        response.setSuccess(status < 400 || status > 599);
        response.setMessage(message);
        response.setData(data);
        response.setStatus(status);
        response.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return response;
    }

    public static ApiErrorResponse generateApiErrorResponse(final String errorMessage, final int errorCode) {
        final var errorResponse = new ApiErrorResponse();

        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setErrorCode(errorCode);
        errorResponse.setTimestamp(DateTimeUtils.getCurrentDateTimeInMilliseconds());

        return errorResponse;
    }

}
