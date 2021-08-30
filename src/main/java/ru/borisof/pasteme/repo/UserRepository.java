package ru.borisof.pasteme.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.pasteme.dao.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}
