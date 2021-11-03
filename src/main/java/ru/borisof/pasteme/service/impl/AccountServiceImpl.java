package ru.borisof.pasteme.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collections;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.borisof.pasteme.app.exception.NotFoundException;
import ru.borisof.pasteme.model.dto.UserRegisterRequest;
import ru.borisof.pasteme.model.entity.User;
import ru.borisof.pasteme.repo.UserRepository;
import ru.borisof.pasteme.security.model.Authority;
import ru.borisof.pasteme.security.repo.AuthorityRepository;
import ru.borisof.pasteme.service.AccountService;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    final UserRepository repo;
    final PasswordEncoder passwordEncoder;
    final AuthorityRepository authorityRepository;

    @Override
    public User registerUser(UserRegisterRequest registerRequest) {

        if (!EmailValidator.getInstance().isValid(registerRequest.getEmail())) {
            throw new ValidationException("Email is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Authority authority = authorityRepository.save(new Authority("user"));

        User user = User.builder()
            .email(registerRequest.getEmail())
            .firstname(registerRequest.getName())
            .password(encodedPassword)
            .authorities(Collections.singleton(authority))
            .build();
        return repo.save(user);
    }

    @Override
    public User getUser(long userId) {
        return repo.findById(userId)
                .orElseThrow(()->new NotFoundException("User not found exception"));
    }

    @Override
    public User changeUserPassword(long userId, String currentPass, String newPass) {

        User currentUserInfo = repo.findById(userId)
                .orElseThrow(()->new NotFoundException("User not found exception"));

        if (!checkCurrentPassword(currentUserInfo, currentPass))
            throw new ValidationException("Password is incorrect");

        currentUserInfo.setPassword(sha1(newPass));
        return repo.save( currentUserInfo );

    }

    @Override
    public boolean userExists(long userId) {
        return repo.existsById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return repo.findUserByEmail(email)
                .orElseThrow(()->new NotFoundException("User not found exception"));
    }

    private boolean checkCurrentPassword(User user, String password){
        if (password == null)
            return false;
        return sha1(password).equals(user.getPassword());
    }

    private boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @SneakyThrows
    private String sha1(String value){
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(value.getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }

}
