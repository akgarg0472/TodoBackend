package com.akgarg.todobackend.service.ip;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akhilesh Garg
 * @since 09-10-2022
 */
public interface IpService {

    String getClientIP(HttpServletRequest request);

}
