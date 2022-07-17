package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.entity.TodoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public interface UserRepository extends MongoRepository<TodoUser, String> {

    Optional<TodoUser> findByEmail(String email);

    Optional<TodoUser> findByIdAndEmail(String id, String email);

}
