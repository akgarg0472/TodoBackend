package com.akgarg.todobackend;

import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.response.PaginatedTodoResponse;
import com.akgarg.todobackend.response.TodoResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TodoBackendApplicationTests {

    @Autowired
    private ApplicationLogger logger;

    @Test
    void testFindCompletedTodosByUserIdMethodOfTodoRepository(
            @Autowired final TodoRepository todoRepository
    ) {
        assertNotNull(todoRepository);

        final PaginatedTodoResponse completedTodosByUserId = todoRepository.findCompletedTodosByUserId("62e4297a4096b23ebe7c6e6d", 0, 10);
        assertNotNull(completedTodosByUserId);

        final List<TodoResponseDto> todos = completedTodosByUserId.getTodos();
        assertNotNull(todos);
    }

    @Test
    void testFindPendingTodosByUserIdMethodOfTodoRepository(
            @Autowired final TodoRepository todoRepository
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
