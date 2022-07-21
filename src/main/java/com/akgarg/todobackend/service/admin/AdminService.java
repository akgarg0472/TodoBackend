package com.akgarg.todobackend.service.admin;

import com.akgarg.todobackend.entity.TodoEntity;
import com.akgarg.todobackend.entity.TodoUser;

import java.util.List;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 21-07-2022
 */
public interface AdminService {

    List<TodoEntity> getAllTodos();

    List<TodoUser> getAllUsers();

    List<TodoEntity> getAllTodosByUserId(String userId);

    boolean lockUserAccount(String userId);

    boolean terminateUserAccount(String userId);

    boolean changeUserAccountType(String userId, String accountType);

}
