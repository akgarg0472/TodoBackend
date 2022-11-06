package com.akgarg.todobackend.config.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Akhilesh Garg
 * @since 14-07-2022
 */
@ConfigurationProperties(prefix = "email.sender")
@Getter
@Setter
@ToString
public class EmailSenderConfigProperties {

    private String host;
    private Integer port;
    private String senderEmail;
    private String senderEmailPassword;
    private String protocol;
    private boolean auth;
    private boolean tlsEnable;
    private boolean debug;

}
