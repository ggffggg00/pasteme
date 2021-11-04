package ru.borisof.pasteme.snippet.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.account.service.AccountService;
import ru.borisof.pasteme.app.exception.NotFoundException;
import ru.borisof.pasteme.app.utils.Hashids;
import ru.borisof.pasteme.security.SecurityUtils;
import ru.borisof.pasteme.snippet.event.CodeSnippetCreatedEvent;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetDetailedDto;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetShortDto;
import ru.borisof.pasteme.snippet.model.entity.CodeSnippet;
import ru.borisof.pasteme.snippet.repo.CodeSnippetRepository;
import ru.borisof.pasteme.snippet.service.CodeSnippetService;

@Service
@RequiredArgsConstructor
public class CodeSnippetServiceImpl implements CodeSnippetService {

  private final CodeSnippetRepository codeSnippetRepository;
  private final AccountService accountService;
  private final Hashids idConverter;
  private ApplicationEventPublisher eventPublisher;

  @Override
  public CodeSnippetDetailedDto getPublicCodeSnippetByExternalId(String externalId) {

    long internalId = idConverter.decode(externalId)[0];

    CodeSnippet codeSnippet = codeSnippetRepository.findById(internalId)
        .orElseThrow(
            () -> new NotFoundException("Code snippet not found or you can't access that"));

    if (!codeSnippet.isPublic()) {
      throw new NotFoundException("Code snippet not found or you can't access that");
    }

    return entityToDto(codeSnippet);
  }

  @Override
  @Transactional
  public Collection<CodeSnippet> getCodeSnippetsBySenderUser(User user) {
    return codeSnippetRepository.findAllBySender(user);
  }

  @Override
  public Collection<CodeSnippetDetailedDto> getCodeSnippetsOfCurrentUser() {
    User currentUser = accountService.getAuthenticatedUser();
    return getCodeSnippetsBySenderUser(currentUser).stream()
        .map(this::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Collection<CodeSnippet> getCodeSnippetsByRecipientUser(User recipient) {
    return codeSnippetRepository.findAllByRecipient(recipient);
  }

  @Override
  public Collection<CodeSnippetDetailedDto> getReceivedCodeSnippetsOfCurrentUser() {
    User currentUser = accountService.getAuthenticatedUser();
    return getCodeSnippetsByRecipientUser(currentUser).stream()
        .map(this::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public CodeSnippetDetailedDto createCodeSnippet(CodeSnippetShortDto codeSnippetShortDto) {

    CodeSnippet snippet = CodeSnippet.builder()
        .title(codeSnippetShortDto.getTitle())
        .content(codeSnippetShortDto.getBase64Content())
        .syntaxType(codeSnippetShortDto.getSyntaxType())
        .build();

    if (SecurityUtils.getAuthorizedUserName().isPresent()){
      snippet.setSender(accountService.getAuthenticatedUser());
    }

    if (StringUtils.hasText(codeSnippetShortDto.getRecipientUsername())) {
      User recipientUser = accountService.getUserDetailsByEmailOrUsername(
              codeSnippetShortDto.getRecipientUsername())
          .orElseThrow(() -> new ValidationException("Recipient user not found"));
      snippet.setRecipient(recipientUser);
    }

    snippet = codeSnippetRepository.save(snippet);

    if (StringUtils.hasText(codeSnippetShortDto.getRecipientUsername())) {
      eventPublisher.publishEvent(new CodeSnippetCreatedEvent(
          snippet.getId(),
          snippet.getRecipient().getId()
      ));
    }

    return entityToDto(snippet);
  }


  private CodeSnippetDetailedDto entityToDto(CodeSnippet codeSnippetEntity) {
    var snippetDto = CodeSnippetDetailedDto.builder()
        .title(codeSnippetEntity.getTitle())
        .base64Content(codeSnippetEntity.getContent())
        .createdAt(codeSnippetEntity.getCreatedAt())
        .syntaxType(codeSnippetEntity.getSyntaxType())
        .isPublic(codeSnippetEntity.isPublic())
        .externalId(idConverter.encode(codeSnippetEntity.getId()))
        .build();

    if (codeSnippetEntity.getRecipient() != null) {
      snippetDto.setRecipientUsername(codeSnippetEntity.getRecipient().getUsername());
    }

    return snippetDto;

  }


}
