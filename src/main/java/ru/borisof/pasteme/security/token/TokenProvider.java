package ru.borisof.pasteme.security.token;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

  String generateToken(Authentication authentication);

  Authentication getAuthenticationByToken(String token);

  boolean validateToken(String token);

}
