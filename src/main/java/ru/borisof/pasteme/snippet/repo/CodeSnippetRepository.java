package ru.borisof.pasteme.snippet.repo;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.snippet.model.entity.CodeSnippet;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Long> {

  Collection<CodeSnippet> findAllBySender(User sender);

  Collection<CodeSnippet> findAllByRecipient(User recipient);


}
