package com.transport.service;

import com.transport.api.dto.user.AuthenticationDto;

public interface AuthenticationService {
    String createAuthenticationToken(AuthenticationDto authenticationRequest);
}
