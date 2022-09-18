package com.akgarg.todobackend.config.email;

import com.akgarg.todobackend.utils.ValidationUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Configuration
@EnableConfigurationProperties(EmailSenderConfigProperties.class)
public class EmailConfig {

    private final EmailSenderConfigProperties config;

    public EmailConfig(EmailSenderConfigProperties config) {
        this.config = config;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        ValidationUtils.checkEmailSenderProperties(config);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getSenderEmail());
        mailSender.setPassword(config.getSenderEmailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", config.getProtocol());
        props.put("mail.smtp.auth", config.isAuth());
        props.put("mail.smtp.starttls.enable", config.isTlsEnable());
        props.put("mail.debug", config.isDebug());

        return mailSender;
    }

}
