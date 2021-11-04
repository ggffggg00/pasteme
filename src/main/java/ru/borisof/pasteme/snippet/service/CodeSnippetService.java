package ru.borisof.pasteme.snippet.service;

import java.util.Collection;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetDetailedDto;
import ru.borisof.pasteme.snippet.model.dto.CodeSnippetShortDto;
import ru.borisof.pasteme.snippet.model.entity.CodeSnippet;

public interface CodeSnippetService {

  CodeSnippetDetailedDto getPublicCodeSnippetByExternalId(String externalId);

  Collection<CodeSnippet> getCodeSnippetsBySenderUser(User user);

  Collection<CodeSnippetDetailedDto> getCodeSnippetsOfCurrentUser();

  Collection<CodeSnippet> getCodeSnippetsByRecipientUser(User recipient);

  Collection<CodeSnippetDetailedDto> getReceivedCodeSnippetsOfCurrentUser();

  CodeSnippetDetailedDto createCodeSnippet(CodeSnippetShortDto codeSnippetShortDto);

}
