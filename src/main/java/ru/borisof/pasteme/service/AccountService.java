package ru.borisof.pasteme.service;

import ru.borisof.pasteme.model.entity.User;
import ru.borisof.pasteme.model.dto.UserRegisterRequest;

public interface AccountService {

    User registerUser(UserRegisterRequest registerRequest);

    User getUser(long userId);

    User changeUserPassword(long userId, String currentPass, String newPass);

    boolean userExists(long userId);

    User getUserByEmail(String email);

}
