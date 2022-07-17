package com.akgarg.todobackend.service.email;

import com.akgarg.todobackend.logger.TodoLogger;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TodoLogger logger;

    @Override
    public boolean send(String toEmail, String subject, String message) {
        logger.info(getClass(), "Send email request received: [to: {}, subject: {}, message: {}]", toEmail, subject, message);

        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setTo(toEmail);
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);

        try {
            this.javaMailSender.send(mimeMessage);
            logger.info(getClass(), "{} email successfully sent to {}", subject, toEmail);
            return true;
        } catch (Exception e) {
            logger.error(getClass(), "Error sending {} email to {}: {}", subject, toEmail, e.getClass().getSimpleName());
            return false;
        }
    }

    @Override
    public boolean sendForgotPasswordEmail(String email) {
        return this.send(email, "Forgot Password", "Forgot password email body");
    }

    @Override
    public boolean sendAccountVerificationEmail(String email) {
        return false;
    }

    @Override
    public boolean sendAccountConfirmSuccessEmail(String email) {
        return false;
    }

    @Override
    public boolean sendPasswordSuccessfullyUpdatedEmail(String email) {
        return false;
    }

    @Override
    public boolean sendAccountDeletedSuccessfullyEmail(String email) {
        return false;
    }


}
