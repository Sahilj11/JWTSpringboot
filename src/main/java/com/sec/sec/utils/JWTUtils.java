package com.sec.sec.utils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * JWTUtils
 */
@Slf4j
public class JWTUtils {

    private JWTUtils() {
    }

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private static final String ISSUER = "This is a test";
    private static final int EXPIRATION_TIME_IN_MINUTES = 10;

    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    public static Optional<String> getUserName(String jwtToken) {
        var claimsOptional = parseToken(jwtToken);
        return claimsOptional.map(Claims::getSubject);
    }

    public static Optional<Claims> parseToken(String jwtToken) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        } catch (Exception e) {
            log.error("Error occured");
        }
        return Optional.empty();
    }

    public static String generateToken(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + EXPIRATION_TIME_IN_MINUTES * 60000); // Adding minutes to
                                                                                                    // the current time
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .compact();
    }

}
