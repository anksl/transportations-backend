package com.transport.service;

import com.transport.api.dto.AuthenticationDto;
import com.transport.api.dto.UserDto;

public interface AuthenticationService {
    String createAuthenticationToken(AuthenticationDto authenticationRequest);
}
