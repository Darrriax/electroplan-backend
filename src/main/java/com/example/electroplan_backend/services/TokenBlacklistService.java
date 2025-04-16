package com.example.electroplan_backend.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class TokenBlacklistService {

    // Додатково: метод для отримання всіх nigga-токенів (наприклад, для очищення)
    private final Map<String, Date> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklistToken(String token, Date expiryDate) {
        blacklistedTokens.put(token, expiryDate);
    }

    public boolean isTokenBlacklisted(String token) {
        Date expiryDate = blacklistedTokens.get(token);
        if (expiryDate == null) {
            return false;
        }

        // Видалення прострочених токенів
        if (expiryDate.before(new Date())) {
            blacklistedTokens.remove(token);
            return false;
        }

        return true;
    }

}
