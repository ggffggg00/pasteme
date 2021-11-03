package ru.borisof.pasteme.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.pasteme.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}
