package net.etfbl.pisio.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import net.etfbl.pisio.authservice.config.JwtProperties;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    private final Integer validity;

    public JwtService(JwtProperties properties) {
        this.algorithm = Algorithm.HMAC512(properties.getSecret());
        this.verifier = JWT.require(algorithm).build();
        this.validity = properties.getValidity();
    }

    public String generateToken(String username) {
        return JWT
                .create()
                .withSubject(username)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(validity)))
                .sign(this.algorithm);
    }

    public String isTokenValid(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (final JWTVerificationException ex) {
            log.debug("Token validation failed", ex);
            return null;
        }
    }

}
