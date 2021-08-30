package ru.borisof.pasteme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.borisof.pasteme.config.JwtProvider;
import ru.borisof.pasteme.dao.User;
import ru.borisof.pasteme.service.AccountService;
import ru.borisof.pasteme.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    @Override
    public boolean hasAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new SecurityException("Not authorized"))
                .getDetails();
    }

    @Override
    public String authenticateUser(String email, String password) {
        User user = accountService.getUserByEmail(email);
        return jwtProvider.generateToken(user.getEmail());
    }
}
