package com.example.electroplan_backend.services;

import com.example.electroplan_backend.dto.entities.UserEntity;
import com.example.electroplan_backend.dto.requests.UserRegistrationRequest;
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
     * @param user дані користувача
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
     * Зміна паролю користувача з хешуванням
     *
     * @return користувач
     */
    public UserEntity changePassword(String email, String newPassword) {
        UserEntity user = getByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
        return user;
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


    public UserEntity changeEmail(String email, String newEmail) {
        UserEntity user = getByEmail(email);
        user.setEmail(newEmail);
        repository.save(user);
        return user;
    }

    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }


}
