package com.akgarg.todobackend.service.logout;

import java.util.Map;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 10-09-2022
 */
public interface LogoutService {

    void logout(Map<String, String> token);

}
