package com.akgarg.todobackend.service.ip;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Akhilesh Garg
 * @since 09-10-2022
 */
@Service
public class IpServiceImpl implements IpService {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
    private static final String LOCALHOST_IPV4_PLACEHOLDER = "LOCALHOST_IPV4";
    private static final String LOCALHOST_IPV6_PLACEHOLDER = "LOCALHOST_IPV6";
    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    @Override
    public String getClientIP(final HttpServletRequest request) {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        for (String header : IP_HEADER_CANDIDATES) {
            final String ipList = request.getHeader(header);

            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                return convertIp(ipList.split(",")[0]);
            }
        }

        return convertIp(request.getRemoteAddr());
    }

    private String convertIp(final String ip) {
        if (StringUtils.hasText(ip)) {
            return switch (ip) {
                case LOCALHOST_IPV4 -> LOCALHOST_IPV4_PLACEHOLDER;
                case LOCALHOST_IPV6 -> LOCALHOST_IPV6_PLACEHOLDER;
                default -> ip;
            };
        } else {
            return ip;
        }
    }

}
