package com.example.creatorconnectbackend.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Extracts the email (used as a subject in this JWT implementation) from the token.
     *
     * @param token The JWT token from which to extract the email.
     * @return The email extracted from the token.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic method to extract a particular claim from the token.
     *
     * @param token          The JWT token from which to extract the claim.
     * @param claimsResolver The function that resolves the claim from the token.
     * @param <T>            The type of the claim.
     * @return The extracted claim value.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a token.
     *
     * @param token The JWT token from which to extract all claims.
     * @return The Claims object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return True if the token has expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Public method to generate a JWT token for a given email.
     *
     * @param email The email for which to generate the JWT token.
     * @return The generated JWT token.
     */
    public String generateToken(String email) {
        return createToken(email);
    }

    /**
     * Generate a JWT token for the given email, with a set expiration time (10 hours in this implementation).
     *
     * @param email The email for which to generate the JWT token.
     * @return The generated JWT token.
     */
    private String createToken(String email) {
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates the token: checks that the token's subject (email) matches the provided email and the token hasn't expired.
     *
     * @param token The JWT token to validate.
     * @param email The email to validate against the token's subject.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
}
