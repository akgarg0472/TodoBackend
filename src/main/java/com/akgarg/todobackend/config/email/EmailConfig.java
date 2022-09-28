package com.akgarg.todobackend.config.email;

import com.akgarg.todobackend.utils.ValidationUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
        props.put("mail.transport.protocol", config.getProtocol());
        props.put("mail.smtp.auth", config.isAuth());
        props.put("mail.smtp.starttls.enable", config.isTlsEnable());
        props.put("mail.debug", config.isDebug());

        return mailSender;
    }

}
