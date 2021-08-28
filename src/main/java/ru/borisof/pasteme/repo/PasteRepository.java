package ru.borisof.pasteme.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.borisof.pasteme.dao.Paste;

import java.util.Optional;

@Repository
public interface PasteRepository extends CrudRepository<Paste, Long> {


}
