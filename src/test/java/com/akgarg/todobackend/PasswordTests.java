package com.akgarg.todobackend;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Akhilesh Garg
 * @since 17-09-2022
 */
@SpringBootTest
class PasswordTests {

    @Autowired
    private ApplicationLogger logger;

    @Test
    void testPasswordUtilMethodsRelatedToAccountVerificationToken() {
        assertNotNull(logger);

        final String originalUserId = "akgarg0472@gmail.com";
        logger.info(getClass(), "Original originalUserId: {}", originalUserId);

        final String generatedAccountVerificationToken = PasswordUtils.generateTokenFromId(originalUserId);
        logger.info(getClass(), "AccountVerificationToken: {}", generatedAccountVerificationToken);

        final String decodedUserId = PasswordUtils.generateIdFromToken(generatedAccountVerificationToken);
        logger.info(getClass(), "Decode userId: {}", decodedUserId);

        Assertions.assertEquals(originalUserId, decodedUserId);

        logger.info(getClass(), "Password Util methods test success");
    }

    @Test
    void testForgotPasswordTokenGenerateLogin() {
        final String userId = "62e4297a4096b23ebe7c6e6d";

        final String forgotPasswordToken = PasswordUtils.generateForgotPasswordToken();
        assertNotNull(forgotPasswordToken);

        final String hashForgotPasswordToken = PasswordUtils.hashForgotPasswordToken(forgotPasswordToken, userId);
        assertNotNull(hashForgotPasswordToken);

        final String[] decodedForgotPasswordToken = PasswordUtils.decodeForgotPasswordToken(hashForgotPasswordToken);
        assertNotNull(decodedForgotPasswordToken);
        assertNotEquals(new String[]{}, decodedForgotPasswordToken);

        logger.info(getClass(), "Encoded Forgot Password token is : {}", forgotPasswordToken);
        logger.info(getClass(), "Decoded Forgot Password token is : {}", Arrays.toString(decodedForgotPasswordToken));
    }

}
