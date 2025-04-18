package com.example.electroplan_backend.controllers;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.ChangePasswordRequest;
import com.example.electroplan_backend.dto.requests.UserUpdateRequest;
import com.example.electroplan_backend.dto.responses.*;
import com.example.electroplan_backend.exceptions.user.UserAlreadyExistsException;
import com.example.electroplan_backend.mappers.ProjectMapper;
import com.example.electroplan_backend.mappers.UserMapper;
import com.example.electroplan_backend.services.ProjectService;
import com.example.electroplan_backend.services.JwtService;
import com.example.electroplan_backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    /**
     * Отримати інформацію про користувача за його ID
     *
     * @param id ID користувача
     * @return UserResponse з інформацією про користувача
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getById(id);
        UserProfileResponse userResponse = userMapper.toUserProfileResponse(userEntity);
        return ResponseEntity.ok(userResponse);
    }


    /**
     * Оновлення даних користувача.
     *
     * @param request        Нові дані користувача.
     * @param authentication Об'єкт автентифікації з поточними даними користувача.
     * @return Відповідь з оновленими даними користувача.
     */
    @PutMapping("/profile")
    public ResponseEntity<ReloginResponse> updateUserProfile (
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication) {

        String currentEmail = authentication.getName();
        boolean emailChanged = request.getEmail() != null && !request.getEmail().equals(currentEmail);

        if (emailChanged && userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email вже використовується: " + request.getEmail());
        }

        UserEntity updatedUser = userService.updateUserProfile(currentEmail, request);
        UserProfileResponse userResponse = userMapper.toUserProfileResponse(updatedUser);

        // Генеруємо новий токен тільки якщо змінився email
        if (emailChanged) {
            String newToken = jwtService.generateToken(updatedUser);
            String message = "Email змінено. Будь ласка, увійдіть знову";
            return ResponseEntity.ok(new ReloginResponse(newToken, userResponse, message));
        }

        return ResponseEntity.ok(new ReloginResponse(null, userResponse, null)); // без повідомлення
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        String currentEmail = authentication.getName();
        userService.changePasswordSecure(currentEmail, request);

        return ResponseEntity.ok("Пароль успішно змінено");
    }


    /**
     * Отримання даних користувача.
     *
     * @param authentication Об'єкт автентифікації з поточними даними користувача.
     * @return Відповідь з оновленими даними користувача.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            Authentication authentication) {

        String currentEmail = authentication.getName();
        UserEntity byEmail = userService.getByEmail(currentEmail);

        UserProfileResponse userProfileResponse = userMapper.toUserProfileResponse(byEmail);
//        userProfileResponse.setUsersProjects(projectService.getAllByUserId(byEmail.getId()));
        return ResponseEntity.ok(userProfileResponse);
    }
}
