package com.globallogic.usermicroservice.Infrastructure.common.configuration;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.globallogic.usermicroservice.application.service.impl.UserService;
import com.globallogic.usermicroservice.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "secretkeyexample";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String createFromUser(User usuario) {
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withIssuer("api")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 900_000)) // 15 minutos
                .sign(ALGORITHM);
    }

    public String getEmailFromToken(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject(); // El subject es el email
    }
}
