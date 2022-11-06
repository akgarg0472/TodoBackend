package com.akgarg.todobackend.service.email;

import com.akgarg.todobackend.cache.ApplicationCache;
import com.akgarg.todobackend.constants.CacheConfigKey;
import com.akgarg.todobackend.logger.ApplicationLogger;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.akgarg.todobackend.constants.EmailConstants.*;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final ApplicationLogger logger;
    private final ApplicationCache cache;
    private final JavaMailSender javaMailSender;

    @Override
    public boolean send(
            final String recipients, final String subject, final String message
    ) {
        try {
            final var emailMessage = this.javaMailSender.createMimeMessage();
            emailMessage.setSubject(subject);

            final var messageHelper = new MimeMessageHelper(emailMessage, true);
            messageHelper.setTo(recipients.split(","));
            messageHelper.setText(message, true);

            this.javaMailSender.send(emailMessage);

            this.logger.info(getClass(), "{}: email successfully sent to -> {}", subject, Arrays.toString(recipients.split(",")));

            return true;
        } catch (Exception e) {
            this.logger.error(getClass(), "Error sending {} email to {}: {}", subject, Arrays.toString(recipients.split(",")), e.getClass().getSimpleName());
        }

        return false;
    }

    @Override
    public boolean sendForgotPasswordEmail(
            final String frontEndResetPasswordEndpointUrl,
            final String name,
            final String email,
            final String forgotPasswordToken
    ) {
        final String forgotPasswordEmailMessage = this.cache.getConfigValue(CacheConfigKey.FORGOT_PASSWORD_EMAIL_MESSAGE.name(), DEFAULT_FORGOT_PASSWORD_EMAIL_MESSAGE);

        return this.send(email, FORGOT_PASSWORD_EMAIL_SUBJECT, forgotPasswordEmailMessage.replace(NAME_PLACEHOLDER, name).replace(FRONT_END_FORGOT_PASSWORD_ENDPOINT_URL_PLACEHOLDER, frontEndResetPasswordEndpointUrl).replace(FORGOT_PASSWORD_TOKEN_PLACEHOLDER, forgotPasswordToken));
    }

    @Override
    public boolean sendAccountVerificationEmail(
            final String email, final String name, final String url, final String accountVerificationToken
    ) {
        final String sendAccountVerificationEmailMessage = this.cache.getConfigValue(CacheConfigKey.ACCOUNT_VERIFICATION_EMAIL.name(), DEFAULT_ACCOUNT_VERIFICATION_EMAIL);

        return this.send(email, ACCOUNT_VERIFICATION_EMAIL_SUBJECT, sendAccountVerificationEmailMessage.replace(NAME_PLACEHOLDER, name).replace(BASE_URL_PLACEHOLDER, url).replace(ACCOUNT_VERIFICATION_TOKEN_PLACEHOLDER, accountVerificationToken));
    }

    @Override
    public void sendAccountVerificationSuccessEmail(String email, String name) {
        String accountConfirmSuccessEmailMessage = this.cache.getConfigValue(CacheConfigKey.ACCOUNT_VERIFY_SUCCESS_EMAIL.name(), DEFAULT_ACCOUNT_VERIFY_SUCCESS_EMAIL);

        this.send(email, ACCOUNT_VERIFICATION_SUCCESS_EMAIL_SUBJECT, accountConfirmSuccessEmailMessage.replace(NAME_PLACEHOLDER, name));
    }

    @Override
    public void sendPasswordSuccessfullyUpdatedEmail(String email, String name) {
        String passwordSuccessfullyUpdatedEmailMessage = this.cache.getConfigValue(CacheConfigKey.PASSWORD_CHANGED_SUCCESS_EMAIL.name(), DEFAULT_PASSWORD_CHANGED_SUCCESS_EMAIL);

        this.send(email, PASSWORD_CHANGED_SUCCESSFULLY_SUBJECT, passwordSuccessfullyUpdatedEmailMessage.replace(NAME_PLACEHOLDER, name));
    }

    @Override
    public boolean sendAccountDeletedSuccessfullyEmail(String email, String name) {
        return false;
    }

}
