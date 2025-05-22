package com.atlas.Atlas_Auth_Service.service;

import com.atlas.Atlas_Auth_Service.model.enums.UserRoles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(String login, UserRoles role) {
        return JWT.create()
                .withIssuer("Atlas")
                .withSubject(login)
                .withClaim("role", role.name())
                .withExpiresAt(genExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }


    public String generateInternalServiceToken () {
        return JWT.create()
                .withIssuer("Atlas")
                .withSubject("auth-service")
                .withClaim("role", "INTERNAL")
                .withExpiresAt(genExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }



    // Metodo inutilizado, validação do token será feita nos outros microsserviços
   public String validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("Atlas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
        return token;
   }


    private Instant genExpirationDate () {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
