package com.akgarg.todobackend.constants;

/**
 * @author Akhilesh Garg
 * @since 21-09-2022
 */
public class EmailConstants {

    // subjects
    public static final String ACCOUNT_VERIFICATION_EMAIL_SUBJECT = "Verify Account";
    public static final String FORGOT_PASSWORD_EMAIL_SUBJECT = "Forgot Password";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY_SUBJECT = "Password changed successfully";
    public static final String ACCOUNT_VERIFICATION_SUCCESS_EMAIL_SUBJECT = "Congratulations 🎉, Account activated successfully";

    // default email messages for different cases
    public static final String DEFAULT_FORGOT_PASSWORD_EMAIL_MESSAGE = "Dear <strong>$NAME</strong>,<br><br>Please click on following link to change password of your account.<br><a href='$FRONT_END_FORGOT_PASSWORD_ENDPOINT_URL/$FORGOT_PASSWORD_TOKEN'>Reset Password</a><br><br>Regards<br>Team Admin";
    public static final String DEFAULT_ACCOUNT_VERIFICATION_EMAIL = "Dear <strong>$NAME</strong>,<br><br>Your account is created successfully. Please click on following link to verify your account.<br><a href='$BASE_URL/api/v1/account/verify/$ACCOUNT_VERIFICATION_TOKEN'>Verify Account</a><br><br>Regards<br>Team Admin";
    public static final String DEFAULT_ACCOUNT_VERIFY_SUCCESS_EMAIL = "Congratulations <strong>$NAME</strong>,<br><br>Your account is successfully activated.<br><br>Regards<br>Team Admin";
    public static final String DEFAULT_PASSWORD_CHANGED_SUCCESS_EMAIL = "Dear <strong>$NAME</strong>,<br><br>Password of your account is successfully changed.<br>If you have not performed this action, please change the password of your account immediately.<br><br>Regards<br>Team Admin";

    // placeholder constants
    public static final String FRONT_END_FORGOT_PASSWORD_ENDPOINT_URL_PLACEHOLDER = "$FRONT_END_FORGOT_PASSWORD_ENDPOINT_URL";
    public static final String BASE_URL_PLACEHOLDER = "$BASE_URL";
    public static final String FORGOT_PASSWORD_TOKEN_PLACEHOLDER = "$FORGOT_PASSWORD_TOKEN";
    public static final String ACCOUNT_VERIFICATION_TOKEN_PLACEHOLDER = "$ACCOUNT_VERIFICATION_TOKEN";
    public static final String NAME_PLACEHOLDER = "$NAME";

    private EmailConstants() {
    }

}
