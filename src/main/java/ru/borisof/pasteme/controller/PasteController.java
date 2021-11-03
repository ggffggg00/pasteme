package ru.borisof.pasteme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.pasteme.model.entity.Paste;
import ru.borisof.pasteme.model.dto.PasteCreationRequest;
import ru.borisof.pasteme.app.exception.NotFoundException;
import ru.borisof.pasteme.service.PasteService;

@RequiredArgsConstructor
@RestController()
@RequestMapping("api")
public class PasteController {

    final PasteService pasteService;


//    Создание пасты
    @PostMapping
    public ResponseEntity<String> createPaste(
            @RequestBody PasteCreationRequest pasteRequest) {

        return new ResponseEntity<String>(pasteService.addPaste(pasteRequest),
                HttpStatus.OK);
    }

    //TODO: Пробросить исключение 404
    @GetMapping("{externalId}")
    public ResponseEntity<Paste> getPaste(@PathVariable String externalId) {

        return new ResponseEntity<>(pasteService.getPasteByExternalId(externalId)
                .orElseThrow(()->new NotFoundException("Paste not found")), HttpStatus.OK);

    }

}
