package ru.borisof.pasteme.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.borisof.pasteme.model.entity.Paste;

@Repository
public interface PasteRepository extends CrudRepository<Paste, Long> {


}
