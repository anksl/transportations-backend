package com.transport.security.config;

import feign.RequestInterceptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@AllArgsConstructor
public class IntegrationFeignConfig {

    private final RequestHeadersHandler requestHeadersHandler;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            HttpHeaders headers = requestHeadersHandler.createHeaders();
            requestHeadersHandler.setAuthHeaders(headers);
            Map<String, Collection<String>> headerMap = new HashMap<>(headers);
            requestTemplate.headers(headerMap);
        };
    }
}

