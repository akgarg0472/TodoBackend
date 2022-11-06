package com.akgarg.todobackend.config.email;

import com.akgarg.todobackend.utils.ValidationUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static com.akgarg.todobackend.constants.EmailEnums.*;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@Configuration
@EnableConfigurationProperties(EmailSenderConfigProperties.class)
public class EmailConfig {

    private final EmailSenderConfigProperties config;

    public EmailConfig(final EmailSenderConfigProperties config) {
        this.config = config;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        ValidationUtils.validateEmailSenderProperties(config);

        final var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getSenderEmail());
        mailSender.setPassword(config.getSenderEmailPassword());

        final var props = mailSender.getJavaMailProperties();
        props.put(TRANSPORT_PROTOCOL.value(), config.getProtocol());
        props.put(SMTP_AUTH.value(), config.isAuth());
        props.put(SMTP_START_TLS_ENABLED.value(), config.isTlsEnable());
        props.put(DEBUG.value(), config.isDebug());

        return mailSender;
    }

}
