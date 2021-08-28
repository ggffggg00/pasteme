package ru.borisof.pasteme.service;

import org.springframework.stereotype.Component;
import ru.borisof.pasteme.dao.Paste;
import ru.borisof.pasteme.dto.PasteCreationRequest;

import java.util.Optional;

public interface PasteService {

    Optional<Paste> getPasteByExternalId(String externalId);

    Optional<Paste> getPasteById(Long id);

    String addPaste(PasteCreationRequest creationRequest);

}
