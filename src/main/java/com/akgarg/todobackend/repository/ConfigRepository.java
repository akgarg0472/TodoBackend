package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.entity.TodoConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-09-2022
 */
@Repository
public interface ConfigRepository extends MongoRepository<TodoConfig, String> {

}
