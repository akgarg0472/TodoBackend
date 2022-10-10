package com.akgarg.todobackend.service.logout;

import java.util.Map;

/**
 * @author Akhilesh Garg
 * @since 10-09-2022
 */
public interface LogoutService {

    void doLogout(Map<String, String> token);

}
