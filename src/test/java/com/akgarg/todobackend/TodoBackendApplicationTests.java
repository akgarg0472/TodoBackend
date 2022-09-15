package com.akgarg.todobackend;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.response.PaginatedTodoResponse;
import com.akgarg.todobackend.response.TodoResponseDto;
import com.akgarg.todobackend.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TodoBackendApplicationTests {

    @Test
    void testFindCompletedTodosByUserIdMethodOfTodoRepository(
            @Autowired final ApplicationLogger logger,
            @Autowired final TodoRepository todoRepository
    ) {
        assertNotNull(todoRepository);
        assertNotNull(logger);

        final PaginatedTodoResponse completedTodosByUserId = todoRepository.findCompletedTodosByUserId("62e4297a4096b23ebe7c6e6d", 0, 10);
        assertNotNull(completedTodosByUserId);
        logger.info(getClass(), "CompletedTodosByUserId instance: {}", completedTodosByUserId);

        final List<TodoResponseDto> todos = completedTodosByUserId.getTodos();
        assertNotNull(todos);
        logger.info(getClass(), "List fetched for completed todos is: {}", todos);

        logger.info(getClass(), "testFindCompletedTodosByUserIdMethodOfTodoRepository() method test success");
    }

    @Test
    void testPasswordUtilMethodsRelatedToAccountVerificationToken(@Autowired final ApplicationLogger logger) {
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
    void testFindPendingTodosByUserIdMethodOfTodoRepository(
            @Autowired final TodoRepository todoRepository,
            @Autowired final ApplicationLogger logger
    ) {
        assertNotNull(todoRepository);
        assertNotNull(logger);

        final PaginatedTodoResponse pendingTodosByUserId = todoRepository.findCompletedTodosByUserId("62e4297a4096b23ebe7c6e6d", 0, 10);
        assertNotNull(pendingTodosByUserId);
        logger.info(getClass(), "CompletedTodosByUserId instance: {}", pendingTodosByUserId);

        final List<TodoResponseDto> todos = pendingTodosByUserId.getTodos();
        assertNotNull(todos);
        logger.info(getClass(), "List fetched for pending todos is: {}", todos);

        logger.info(getClass(), "testFindCompletedTodosByUserIdMethodOfTodoRepository() method test success");
    }


}
