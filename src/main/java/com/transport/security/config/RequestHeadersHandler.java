package com.transport.security.config;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestHeadersHandler {

    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final String COOKIE_HEADER = "Cookie";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String JWT_HEADER_VALUE = "JWT=%s";

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        return headers;
    }

    public void setAuthHeaders(HttpHeaders headers) {
        String authHeaderValue = extractHeaderValue(AUTHORIZATION_HEADER);
        String cookieHeaderValue = extractHeaderValue(COOKIE_HEADER);
        String jwtHeaderValue = String.format(JWT_HEADER_VALUE, getToken());

        headers.add(AUTHORIZATION_HEADER, authHeaderValue);
        headers.add(COOKIE_HEADER, cookieHeaderValue);
        headers.add(COOKIE_HEADER, jwtHeaderValue);
    }

    public String getToken() {
        return extractHeaderValue(RequestHeadersHandler.AUTHORIZATION_HEADER)
            .substring(AUTHORIZATION_PREFIX.length());
    }

    private String extractHeaderValue(String header) {
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(attributes)
            .map(ServletRequestAttributes::getRequest)
            .map(request -> request.getHeader(header))
            .orElseThrow();
    }
}
