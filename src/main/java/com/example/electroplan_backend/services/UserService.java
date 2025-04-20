package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.ChangePasswordRequest;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.requests.UserUpdateRequest;
import com.example.electroplan_backend.exceptions.password.InvalidOldPasswordException;
import com.example.electroplan_backend.exceptions.password.InvalidPasswordException;
import com.example.electroplan_backend.exceptions.password.PasswordsDoNotMatchException;
import com.example.electroplan_backend.mappers.UserMapper;
import com.example.electroplan_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserEntity create(UserRegistrationRequest user) {
        return create(user, true);
    }

    /**
     * Створення користувача
     *
     * @param user         дані користувача
     * @param hashPassword чи хешувати пароль
     * @return користувач
     */
    public UserEntity create(UserRegistrationRequest user, boolean hashPassword) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Акаунт на цю пошту вже зареєстровано!");
        }

        UserEntity userEntity = mapper.toUserEntity(user);
        if (hashPassword) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        repository.save(userEntity);
        return userEntity;
    }

    /**
     * Оновлення профілю користувача
     */
    public UserEntity updateUserProfile(String currentEmail, UserUpdateRequest request) {
        UserEntity user = repository.findByEmail(currentEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));

        if (request.getName() != null && !request.getName().equals(user.getName())) {
            user.setName(request.getName());
        }

        if (request.getSurname() != null && !request.getSurname().equals(user.getSurname())) {
            user.setSurname(request.getSurname());
        }

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            user.setEmail(request.getEmail());
        }

        return repository.save(user);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));

        // Перевірка підтвердження нового пароля
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new PasswordsDoNotMatchException("Новий пароль і його підтвердження не співпадають");
        }

        // Перевірка старого пароля
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Старий пароль неправильний");
        }

        // Перевірка, що новий пароль не такий самий, як старий
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Новий пароль не може бути таким самим, як старий");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
    }


    /**
     * Отримання користувача за email
     *
     * @return користувач
     */
    public UserEntity getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));
    }

    /**
     * Перевірка наявності користувача за email
     *
     * @return true, якщо користувач існує
     */
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    /**
     * Отримання користувача за ID
     *
     * @param id ID користувача
     * @return UserEntity
     */
    public UserEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username);
    }

    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }


}
