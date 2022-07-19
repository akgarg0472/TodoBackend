package com.akgarg.todobackend.controller;

import com.akgarg.todobackend.logger.TodoLogger;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 19-07-2022
 */
@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public class AdminController {

    private final TodoLogger todoLogger;

}
