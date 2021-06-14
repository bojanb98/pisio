package net.etfbl.pisio.authservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import net.etfbl.pisio.authservice.config.JwtProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
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
                .withExpiresAt(new Date(System.currentTimeMillis() + validity))
                .sign(this.algorithm);
    }

    public boolean isTokenValid(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (final JWTVerificationException ex) {
            return false;
        }
    }

}
