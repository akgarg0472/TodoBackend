package com.akgarg.todobackend.config.todo;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@Configuration
public class TodoConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
