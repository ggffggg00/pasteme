package ru.borisof.pasteme.service;

import ru.borisof.pasteme.dao.User;

public interface AuthService {

    boolean hasAuthenticated();

    User getAuthenticatedUser();

    String authenticateUser(String email, String password);

}
