package com.akgarg.todobackend.service.email;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SuppressWarnings("unused")
public interface EmailService {

    boolean send(String toEmail, String subject, String message);

    boolean sendForgotPasswordEmail(
            String frontEndResetPasswordEndpointUrl,
            String name,
            String email,
            String forgotPasswordToken
    );

    boolean sendAccountVerificationEmail(String email, String name, String url, String accountVerificationToken);

    void sendAccountVerificationSuccessEmail(String email, String name);

    void sendPasswordSuccessfullyUpdatedEmail(String email, String name);

    boolean sendAccountDeletedSuccessfullyEmail(String email, String name);

}
