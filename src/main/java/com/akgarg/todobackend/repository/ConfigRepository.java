package com.akgarg.todobackend.repository;

import com.akgarg.todobackend.model.entity.TodoConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Akhilesh Garg
 * @since 19-09-2022
 */
@Repository
public interface ConfigRepository extends MongoRepository<TodoConfig, String> {

}
