package com.akgarg.todobackend.service.email;

import com.akgarg.todobackend.logger.ApplicationLogger;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

import static com.akgarg.todobackend.constants.ApplicationConstants.*;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final ApplicationLogger logger;

    @Override
    public boolean send(String toEmail, String subject, String message) {
        logger.info(getClass(), "Send email request received: [to: {}, subject: {}, message: {}]", toEmail, subject, message);

        try {
            MimeMessage emailMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(emailMessage, true);
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);
            this.javaMailSender.send(emailMessage);
            logger.info(getClass(), "{}: email successfully sent to -> {}", subject, toEmail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(getClass(), "Error sending {} email to {}: {}", subject, toEmail, e.getClass().getSimpleName());
            return false;
        }
    }

    @Override
    public boolean sendForgotPasswordEmail(String url, String email, String forgotPasswordToken) {
        String forgotPasswordEmailMessage = "Your forgot password token is: " + forgotPasswordToken;

        return this.send(email, "Forgot Password", forgotPasswordEmailMessage);
    }

    @Override
    public boolean sendAccountVerificationEmail(String email, String url, String accountVerificationToken) {
        String sendAccountVerificationEmailMessage = "Congratulations, your account is created successfully." +
                "Please click on following link to verify your account.<br>" +
                "<a href='" +
                url + "/api/v1/account/verify/" + accountVerificationToken +
                "'>Verify Account</a>" +
                "<br>" +
                "<br>" +
                "Regards" +
                "Admin";

        return this.send(email, ACCOUNT_VERIFICATION_EMAIL_SUBJECT, sendAccountVerificationEmailMessage);
    }

    @Override
    public void sendAccountConfirmSuccessEmail(String email) {
        String accountConfirmSuccessEmailMessage = "Congratulations, your account is activated successfully." +
                "You can now use our todo service.<br>" +
                "Thanks" +
                "<br>" +
                "<br>" +
                "Regards" +
                "Admin";

        this.send(email, ACCOUNT_VERIFICATION_SUCCESS_EMAIL_SUBJECT, accountConfirmSuccessEmailMessage);
    }

    @Override
    public void sendPasswordSuccessfullyUpdatedEmail(String email) {
        String passwordSuccessfullyUpdatedEmailMessage = "Dear " + email + ". " +
                "Password of your account is successfully changed..";

        this.send(email, PASSWORD_CHANGED_SUCCESSFULLY_SUBJECT, passwordSuccessfullyUpdatedEmailMessage);
    }

    @Override
    public boolean sendAccountDeletedSuccessfullyEmail(String email) {
        return false;
    }


}
