package ru.borisof.pasteme.account.service;

import java.util.Optional;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.account.model.dto.UserRegisterRequest;

public interface AccountService {

    User registerUser(UserRegisterRequest registerRequest);

    Optional<User> getUserDetailsByEmailOrUsername(String emailOrUsername);

    User getAuthenticatedUser();

    boolean isUserExists(String emailOrUsername);

}
