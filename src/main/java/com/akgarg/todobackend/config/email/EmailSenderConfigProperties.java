package com.akgarg.todobackend.config.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 14-07-2022
 */
@ConfigurationProperties(prefix = "email.sender")
public class EmailSenderConfigProperties {

    private String host;
    private int port;
    private String senderEmail;
    private String senderEmailPassword;
    private String protocol;
    private boolean auth;
    private boolean tlsEnable;
    private boolean debug;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderEmailPassword() {
        return senderEmailPassword;
    }

    public void setSenderEmailPassword(String senderEmailPassword) {
        this.senderEmailPassword = senderEmailPassword;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isTlsEnable() {
        return tlsEnable;
    }

    public void setTlsEnable(boolean tlsEnable) {
        this.tlsEnable = tlsEnable;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "EmailSenderConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", senderEmail='" + senderEmail + '\'' +
                ", senderEmailPassword='" + senderEmailPassword + '\'' +
                ", protocol='" + protocol + '\'' +
                ", auth=" + auth +
                ", tlsEnable=" + tlsEnable +
                ", debug=" + debug +
                '}';
    }

}
