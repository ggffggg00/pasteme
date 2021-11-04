package ru.borisof.pasteme.snippet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.borisof.pasteme.snippet.model.SyntaxType;

@Data
public class CodeSnippetShortDto {

  private String title;

  @JsonProperty("content")
  private String base64Content;

  private SyntaxType syntaxType;

  private String recipientUsername;

}
