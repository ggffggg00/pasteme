package ru.borisof.pasteme.account.service.impl;

import java.util.Collections;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.borisof.pasteme.account.model.dto.UserRegisterRequest;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.account.repo.UserRepository;
import ru.borisof.pasteme.account.service.AccountService;
import ru.borisof.pasteme.security.SecurityUtils;
import ru.borisof.pasteme.security.model.Authority;
import ru.borisof.pasteme.security.repo.AuthorityRepository;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final AuthorityRepository authorityRepository;

    @Override
    public User registerUser(UserRegisterRequest registerRequest) {

        if (!EmailValidator.getInstance().isValid(registerRequest.getEmail())) {
            throw new ValidationException("Email is incorrect");
        }

        if (isUserExists(registerRequest.getUsername())){
            throw new ValidationException("User with same username already exists");
        }

        if (isUserExists(registerRequest.getEmail())){
            throw new ValidationException("User with same email already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Authority authority = authorityRepository.save(new Authority("ROLE_USER"));

        User user = User.builder()
            .email(registerRequest.getEmail())
            .firstname(registerRequest.getFirstname())
            .lastname(registerRequest.getLastname())
            .username(registerRequest.getUsername())
            .password(encodedPassword)
            .authorities(Collections.singleton(authority))
            .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserDetailsByEmailOrUsername(String emailOrUsername) {
        if (EmailValidator.getInstance().isValid(emailOrUsername)) {
            return userRepository.findUserByEmailIgnoreCase(emailOrUsername);
        } else {
            return userRepository.findUserByUsername(emailOrUsername);
        }
    }

    @Override
    public User getAuthenticatedUser() {
        String username = SecurityUtils.getAuthorizedUserName()
            .orElseThrow(() -> new AccessDeniedException("User not authenticated"));

        return getUserDetailsByEmailOrUsername(username)
            .orElseThrow(() -> new AccessDeniedException("Unknown user"));
    }

    @Override
    public boolean isUserExists(String emailOrUsername) {
        if (EmailValidator.getInstance().isValid(emailOrUsername)) {
            return userRepository.existsByEmailIgnoreCase(emailOrUsername);
        } else {
            return userRepository.existsByUsername(emailOrUsername);
        }
    }

}
