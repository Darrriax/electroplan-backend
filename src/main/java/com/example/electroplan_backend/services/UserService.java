package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.ChangePasswordRequest;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
import com.example.electroplan_backend.dto.requests.UserUpdateRequest;
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

    /**
     * Зміна паролю з перевіркою старого та підтвердженням нового
     */
    public UserEntity changePasswordSecure(String email, ChangePasswordRequest request) {
        UserEntity user = getByEmail(email);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Старий пароль введено невірно");
        }

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new RuntimeException("Новий пароль і підтвердження не співпадають");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return repository.save(user);
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
