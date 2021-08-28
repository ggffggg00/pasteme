package ru.borisof.pasteme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import ru.borisof.pasteme.dao.Paste;
import ru.borisof.pasteme.dto.PasteCreationRequest;
import ru.borisof.pasteme.service.PasteService;

import java.util.concurrent.ForkJoinPool;

@RestController("/")
public class PasteController {

    final PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public DeferredResult<ResponseEntity<String>> createPaste(@RequestBody PasteCreationRequest pasteRequest) {
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(()->{
            result.setResult(new ResponseEntity<String>(
                    pasteService.addPaste(pasteRequest), HttpStatus.ACCEPTED));
        });
        return result;
    }

    //TODO: Пробросить исключение 404
    @GetMapping("{externalId}")
    public DeferredResult<ResponseEntity<Paste>> getPaste(@PathVariable String externalId) {
        DeferredResult<ResponseEntity<Paste>> output = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {

            output.setResult(new ResponseEntity<Paste>(
                    pasteService.getPasteByExternalId(externalId)
                            .orElseThrow(RuntimeException::new),
                    HttpStatus.ACCEPTED));
        });
        return output;
    }

}
