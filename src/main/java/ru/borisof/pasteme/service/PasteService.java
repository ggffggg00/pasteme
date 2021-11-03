package ru.borisof.pasteme.service;

import ru.borisof.pasteme.model.entity.Paste;
import ru.borisof.pasteme.model.dto.PasteCreationRequest;

import java.util.Optional;

public interface PasteService {

    Optional<Paste> getPasteByExternalId(String externalId);

    Optional<Paste> getPasteById(Long id);

    String addPaste(PasteCreationRequest creationRequest);

}
