package ru.borisof.pasteme.snippet.controller;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetDetailedDto;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetShortDto;
import ru.borisof.pasteme.snippet.service.CodeSnippetService;

@RestController
@RequestMapping("/api/snippet")
@RequiredArgsConstructor
public class CodeSnippetController {

  private final CodeSnippetService snippetService;

  @PostMapping("")
  public ResponseEntity<CodeSnippetDetailedDto> createCodeSnippet(
      @RequestBody CodeSnippetShortDto codeSnippetShortDto) {

    CodeSnippetDetailedDto snippet = snippetService.createCodeSnippet(codeSnippetShortDto);
    return new ResponseEntity<>(snippet, HttpStatus.CREATED);

  }

  @GetMapping("")
  public ResponseEntity<Collection<CodeSnippetDetailedDto>> getUserCodeSnippets() {

    var snippets = snippetService.getCodeSnippetsOfCurrentUser();
    return ResponseEntity.ok(snippets);
  }

  @GetMapping("/{externalId}")
  public ResponseEntity<CodeSnippetDetailedDto> getCodeSnippet(@PathVariable String externalId) {

    var snippet = snippetService.getPublicCodeSnippetByExternalId(externalId);
    return ResponseEntity.ok(snippet);

  }

}
