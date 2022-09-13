package com.akgarg.todobackend.service.admin;

import com.akgarg.todobackend.entity.TodoEntity;
import com.akgarg.todobackend.entity.TodoUser;
import com.akgarg.todobackend.logger.ApplicationLogger;
import com.akgarg.todobackend.repository.TodoRepository;
import com.akgarg.todobackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 21-07-2022
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final ApplicationLogger logger;

    @Override
    public List<TodoEntity> getAllTodos() {
        return null;
    }

    @Override
    public List<TodoUser> getAllUsers() {
        return null;
    }

    @Override
    public List<TodoEntity> getAllTodosByUserId(String userId) {
        return null;
    }

    @Override
    public boolean lockUserAccount(String userId) {
        return false;
    }

    @Override
    public boolean terminateUserAccount(String userId) {
        return false;
    }

    @Override
    public boolean changeUserAccountType(String userId, String accountType) {
        return false;
    }

}
