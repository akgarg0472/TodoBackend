package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.entity.TodoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface TodoRepository extends MongoRepository<TodoEntity, String> {

    Optional<List<TodoEntity>> findAllByUserId(String userId);

}
