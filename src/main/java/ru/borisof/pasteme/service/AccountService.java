package ru.borisof.pasteme.service;

import java.util.Optional;
import ru.borisof.pasteme.model.entity.User;
import ru.borisof.pasteme.model.dto.UserRegisterRequest;

public interface AccountService {

    User registerUser(UserRegisterRequest registerRequest);

    Optional<User> getUserDetailsByEmailOrUsername(String emailOrUsername);

    boolean isUserExists(String emailOrUsername);

}
