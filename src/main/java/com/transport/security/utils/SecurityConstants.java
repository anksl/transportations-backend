package com.transport.security.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
    public static final String SECRET = Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    public static final String ROLES = "ROLES";
    public static final String ROLE = "ROLE_";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String EMPTY = "";
    public static final String[] TRANSPORTER_ENDPOINTS = new String[]
            {
                    "/api/transportations/current",
                    "/api/transportations/period",
                    "/api/transportations/income",
                    "/api/transportations/fuelCost",
                    "/api/transportations/fuelConsumption",
                    "/api/transportations/distance"
            };
}
