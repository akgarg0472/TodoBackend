package com.akgarg.todobackend.service.email;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface EmailService {

    boolean send(String toEmail, String subject, String message);

    boolean sendForgotPasswordEmail(String url, String email, String forgotPasswordToken);

    boolean sendAccountVerificationEmail(String email, String url, String accountVerificationToken);

    void sendAccountConfirmSuccessEmail(String email);

    void sendPasswordSuccessfullyUpdatedEmail(String email);

    boolean sendAccountDeletedSuccessfullyEmail(String email);

}
