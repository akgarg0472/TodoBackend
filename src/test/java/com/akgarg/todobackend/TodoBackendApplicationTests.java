package com.akgarg.todobackend;

import com.akgarg.todobackend.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoBackendApplicationTests {

    @Test
    void testPasswordUtilMethodsRelatedToAccountVerificationToken() {
        String originalUserId = "akgarg0472@gmail.com";
        System.out.println("Original originalUserId: " + originalUserId);

        String generatedAccountVerificationToken = PasswordUtils.generateTokenFromId(originalUserId);
        System.out.println("AccountVerificationToken: " + generatedAccountVerificationToken);

        String decodedUserId = PasswordUtils.generateIdFromToken(generatedAccountVerificationToken);
        System.out.println("Decode userId: " + decodedUserId);

        Assertions.assertEquals(originalUserId, decodedUserId);
    }

}
