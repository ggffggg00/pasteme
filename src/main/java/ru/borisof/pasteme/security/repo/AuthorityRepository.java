package ru.borisof.pasteme.security.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.pasteme.security.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

  Optional<Authority> findAuthorityByName(String name);

}
