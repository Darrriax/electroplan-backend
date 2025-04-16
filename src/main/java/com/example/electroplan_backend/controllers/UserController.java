package com.example.electroplan_backend.controllers;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.ChangeEmailRequest;
import com.example.electroplan_backend.dto.requests.UserPhoneRequest;
import com.example.electroplan_backend.dto.requests.UserUpdateRequest;
import com.example.electroplan_backend.dto.responses.*;
import com.example.electroplan_backend.exceptions.user.InvalidUserParametersException;
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
    public ResponseEntity<UserShortResponse> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getById(id);
        UserShortResponse userResponse = userMapper.toUserResponse(userEntity);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Отримати номер телефону користувача за допомогою запиту
     *
     * @param request запит з ID користувача
     * @return UserPhoneResponse з номером телефону
     */
    @PostMapping("/phone")
    public ResponseEntity<UserPhoneResponse> getUserPhone(@Valid @RequestBody UserPhoneRequest request) {
        UserEntity userEntity = userService.getById(request.getUserId());
        return ResponseEntity.ok(new UserPhoneResponse(userEntity.getPhoneNumber()));
    }


    /**
     * Change the user's email.
     *
     * @param request        Contains the new email.
     * @param authentication The authentication object containing the current user's details.
     * @return A new JWT token with the updated email.
     */
    @PutMapping("/email")
    public ResponseEntity<JwtAuthenticationResponse> changeEmail(
            @Valid @RequestBody ChangeEmailRequest request,
            Authentication authentication) {

        String newEmail = request.getNewEmail();

        // Check if the new email is already in use
        if (userService.existsByEmail(newEmail)) {
            throw new UserAlreadyExistsException("Email is already registered: " + request.getNewEmail());
        }
        String currentEmail = authentication.getName();
        // Generate a new JWT token with the updated email
        String newJwtToken = jwtService.generateToken(userService.changeEmail(currentEmail, newEmail));
        return ResponseEntity.ok(new JwtAuthenticationResponse(newJwtToken));
    }


    /**
     * Оновлення даних користувача.
     *
     * @param request        Нові дані користувача.
     * @param authentication Об'єкт автентифікації з поточними даними користувача.
     * @return Відповідь з оновленими даними користувача.
     */
    @PutMapping("/profile")
    public ResponseEntity<UserUpdateResponse> updateUserProfile (
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication) throws InvalidUserParametersException {

        String currentEmail = authentication.getName();



        if (request.getPassword() != null) {
            userService.changePassword(currentEmail, request.getPassword());
        }

        // Отримуємо користувача за поточним email
        UserEntity userEntity = userService.getByEmail(currentEmail);

        if (request.getName() != null) {
            userEntity.setName(request.getName());
        }

        if (request.getSurname() != null) {
            userEntity.setSurname(request.getSurname());
        }

        if (request.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(request.getPhoneNumber());
        }

        // Мапимо оновлений користувач на DTO для відповіді
        UserUpdateResponse userResponse = userMapper.toUserUpdateResponse(userService.save(userEntity));
        return ResponseEntity.ok(userResponse);
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
        userProfileResponse.setUsersProjects(projectService.getAllByUserId(byEmail.getId()));
        return ResponseEntity.ok(userProfileResponse);
    }
}
