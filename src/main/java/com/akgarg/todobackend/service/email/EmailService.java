package com.akgarg.todobackend.service.email;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface EmailService {

    boolean send(String toEmail, String subject, String message);

    boolean sendForgotPasswordEmail(String email);

    boolean sendAccountVerificationEmail(String email);

    boolean sendAccountConfirmSuccessEmail(String email);

    boolean sendPasswordSuccessfullyUpdatedEmail(String email);

    boolean sendAccountDeletedSuccessfullyEmail(String email);

}
