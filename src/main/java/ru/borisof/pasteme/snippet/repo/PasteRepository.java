package ru.borisof.pasteme.snippet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.pasteme.snippet.model.entity.Paste;

public interface PasteRepository extends JpaRepository<Paste, Long> {



}
