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
import org.springframework.web.context.request.async.DeferredResult;
import ru.borisof.pasteme.dao.Paste;
import ru.borisof.pasteme.dto.PasteCreationRequest;
import ru.borisof.pasteme.exception.NotFoundException;
import ru.borisof.pasteme.service.PasteService;

import java.util.concurrent.ForkJoinPool;

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
