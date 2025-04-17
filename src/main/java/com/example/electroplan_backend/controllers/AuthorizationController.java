package com.example.electroplan_backend.controllers;

import com.example.electroplan_backend.dto.requests.UserLoginRequest;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.responses.JwtAuthenticationResponse;
import com.example.electroplan_backend.dto.responses.LoginResponse;
import com.example.electroplan_backend.exceptions.user.InvalidCredentialsException;
import com.example.electroplan_backend.exceptions.user.UserAlreadyExistsException;
import com.example.electroplan_backend.services.AuthenticationService;
import com.example.electroplan_backend.services.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизація", description = "Контролер для реєстрації та логіну користувачів")
public class AuthorizationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    /**
     * Реєстрація нового користувача
     *
     * @param request дані для реєстрації
     * @return JWT токен
     */
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@Valid @RequestBody UserRegistrationRequest request) throws UserAlreadyExistsException {
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Логінізація користувача
     *
     * @param request дані для логіну
     * @return JWT токен
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signIn(@Valid @RequestBody UserLoginRequest request) throws InvalidCredentialsException {
        LoginResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
public ResponseEntity<?> logout(HttpServletRequest request, Authentication authentication) {
    // Отримуємо заголовок Authorization
    String authHeader = request.getHeader("Authorization");

    if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
        String jwt = authHeader.substring(7);

        String usernameFromToken = jwtService.extractUserName(jwt);
        String usernameFromAuth = authentication.getName();

        if (usernameFromToken != null && usernameFromToken.equals(usernameFromAuth)) {
            jwtService.blacklistToken(jwt);
            return ResponseEntity.ok("Успішний вихід з системи");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Токен не належить аутентифікованому користувачеві");
        }
    } else {
        return ResponseEntity.badRequest().body("JWT токен не знайдено в заголовках запиту");
    }
}



}
