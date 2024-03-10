package com.dnd_app.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dnd_app.dto.ClientDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtManipulator {
    private final String JWT_PREFIX = "Bearer ";
    private final String issuer;
    private final Algorithm signingAlgorithm;
    private final long expirationMs;

    @Autowired
    public JwtManipulator(
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expirationMs}") int expirationMs
    ) {
        this.issuer = issuer;
        this.expirationMs = expirationMs;
        this.signingAlgorithm = Algorithm.HMAC256(secret);
    }

    public TimedJwt generateToken(ClientDTO clientDTO) {
        return new TimedJwt(
                JWT.create()
                        .withSubject(clientDTO.getEmail())
                        .withIssuedAt(new Date())
                        .withClaim("id", clientDTO.getId())
                        .withClaim("email", clientDTO.getEmail())
                        .withClaim("authorities", determineAuthorities(clientDTO))
                        .withExpiresAt(new Date(new Date().getTime() + expirationMs))
                        .withIssuer(issuer)
                        .sign(signingAlgorithm),
                expirationMs
        );
    }

    public List<String> determineAuthorities(ClientDTO user) {
        List<String> authorities = new ArrayList<>();
        authorities.add("Client");
        return authorities;
    }

    public DecodedJWT decodeToken(String token) {
        return JWT.require(signingAlgorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    public Optional<String> getJwt(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (!isValid(jwt))
            return Optional.empty();

        return Optional.of(jwt.substring(JWT_PREFIX.length()));
    }

    private boolean isValid(String jwt) {
        if (jwt == null || jwt.isEmpty())
            return false;

        return jwt.length() > JWT_PREFIX.length() && jwt.startsWith(JWT_PREFIX);
    }
}
