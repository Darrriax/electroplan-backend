package com.example.electroplan_backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Витяг імені користувача з токена
     *
     * @param token токен
     * @return ім'я користувача
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерація токена
     *
     * @param userDetails дані користувача
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("email", customUserDetails.getUsername());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Перевірка токена на валідність
     *
     * @param token       токен
     * @param userDetails дані користувача
     * @return true, якщо токен валідний
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()))
                && !isTokenExpired(token)
                && !tokenBlacklistService.isTokenBlacklisted(token);
    }

    /**
     * Витяг даних з токена
     *
     * @param token           токен
     * @param claimsResolvers функція витягу даних
     * @param <T>             тип даних
     * @return дані
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерація токена
     *
     * @param extraClaims додаткові дані
     * @param userDetails дані користувача
     * @return токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Перевірка токена на прострочення
     *
     * @param token токен
     * @return true, якщо токен прострочений
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Витяг дати закінчення строку дії токена
     *
     * @param token токен
     * @return дата закінчення строку дії
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * Додавання токена в чорний список
     *
     * @param token токен
     */
    public void blacklistToken(String token) {
        Date expiryDate = extractExpiration(token);
        tokenBlacklistService.blacklistToken(token, expiryDate);
    }

    /**
     * Витяг усіх даних з токена
     *
     * @param token токен
     * @return дані
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Отримання ключа для підпису токена
     *
     * @return ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
