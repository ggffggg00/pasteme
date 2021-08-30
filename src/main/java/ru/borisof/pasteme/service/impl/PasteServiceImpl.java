package ru.borisof.pasteme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.borisof.pasteme.commons.Hashids;
import ru.borisof.pasteme.dao.Paste;
import ru.borisof.pasteme.dto.PasteCreationRequest;
import ru.borisof.pasteme.repo.PasteRepository;
import ru.borisof.pasteme.service.PasteService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
public class PasteServiceImpl implements PasteService {

    final Hashids hashidsGenerator;
    final Base64.Decoder base64Decoder;
    final Base64.Encoder base64Encoder;
    final PasteRepository pasteRepository;

    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository) {
        this.hashidsGenerator = new Hashids("122414", 5);
        this.pasteRepository = pasteRepository;
        this.base64Decoder = Base64.getDecoder();
        this.base64Encoder = Base64.getEncoder();
    }

    @Override
    public Optional<Paste> getPasteByExternalId(String externalId) {
        return pasteRepository.findById(
                hashidsGenerator.decode(externalId)[0]);
    }

    @Override
    public Optional<Paste> getPasteById(Long id) {
        return pasteRepository.findById(id);
    }

    @Override
    public String addPaste(PasteCreationRequest creationRequest) {

        Paste paste = Paste.builder()
                .title(creationRequest.getTitle() == null ? "" : creationRequest.getTitle())
                .syntaxType(Paste.PasteSyntaxType.valueOf(
                        creationRequest.getSyntaxType()))
                .content(encrypt(creationRequest.getContent()))
                .build();

        paste = pasteRepository.save(paste);
        return hashidsGenerator.encode(paste.getId());
    }

    private byte[] encrypt(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    private String decrypt(byte[] encrypted) {
        return new String(encrypted, StandardCharsets.UTF_8);
    }
}
