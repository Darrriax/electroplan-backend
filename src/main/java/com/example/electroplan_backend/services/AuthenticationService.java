package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.UserLoginRequest;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.responses.JwtAuthenticationResponse;
import com.example.electroplan_backend.dto.responses.LoginResponse;
import com.example.electroplan_backend.dto.responses.UserShortResponse;
import com.example.electroplan_backend.exceptions.user.InvalidCredentialsException;
import com.example.electroplan_backend.exceptions.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Реєстрація користувача
     *
     * @param request дані користувача
     * @return JWT токен
     * @throws UserAlreadyExistsException якщо користувач із такою поштою вже зареєстрований
     */
    public JwtAuthenticationResponse signUp(UserRegistrationRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Користувач із таким email вже зареєстрований: " + request.getEmail());
        }

        UserEntity userEntity = userService.create(request);

        var jwt = jwtService.generateToken(userEntity);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентифікація користувача
     *
     * @param request дані користувача
     * @return JWT токен
     * @throws InvalidCredentialsException якщо ім'я або пароль неправильні
     */
    public LoginResponse signIn(UserLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Неправильне ім'я користувача або пароль");
        }

        UserEntity user = userService.getByEmail(request.getEmail());
        String jwt = jwtService.generateToken(user);

        // Створюємо UserShortResponse
        UserShortResponse userResponse = new UserShortResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setEmail(user.getEmail());

        // Повертаємо об'єкт з токеном і даними користувача
        return LoginResponse.builder()
                .token(jwt)
                .user(userResponse)
                .build();
    }
}
