package com.akgarg.todobackend;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
 */
@SpringBootTest
class TodoBackendApplicationTests {

    @Autowired
    private ApplicationLogger logger;

    @Test
    void testFindCompletedTodosByUserIdMethodOfTodoRepository(
            @Autowired final TodoRepository todoRepository
    ) {
        assertNotNull(todoRepository);

        final var completedTodosByUserId = todoRepository.findCompletedTodosByUserId("62e4297a4096b23ebe7c6e6d", 0, 10);
        assertNotNull(completedTodosByUserId);

        final var todos = completedTodosByUserId.getTodos();
        assertNotNull(todos);
    }

    @Test
    void testFindPendingTodosByUserIdMethodOfTodoRepository(
            @Autowired final TodoRepository todoRepository
    ) {
        assertNotNull(todoRepository);
        assertNotNull(logger);

        final var pendingTodosByUserId = todoRepository.findCompletedTodosByUserId("62e4297a4096b23ebe7c6e6d", 0, 10);
        assertNotNull(pendingTodosByUserId);
        logger.info(getClass(), "CompletedTodosByUserId instance: {}", pendingTodosByUserId);

        final var todos = pendingTodosByUserId.getTodos();
        assertNotNull(todos);
        logger.info(getClass(), "List fetched for pending todos is: {}", todos);

        logger.info(getClass(), "testFindCompletedTodosByUserIdMethodOfTodoRepository() method test success");
    }

}
