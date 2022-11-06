package com.akgarg.todobackend;

import com.akgarg.todobackend.service.email.EmailService;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Akhilesh Garg
 * @since 16-10-2022
 */
// @SpringBootTest
@SuppressWarnings("unused")
class EmailSenderServiceTest {

    // @Autowired
    private EmailService emailService;

    // @Test
    void testSendEmailMethod() {
        assertNotNull(emailService, "Email Service can't be null");

        final String to = "akgarg0472@gmail.com";
        final String subject = "Demo email";
        final String message = "This is a demo email body";

        final boolean send = emailService.send(to, subject, message);
        assertNotEquals(send, false, "Email send failed due to some exception");
    }

}
