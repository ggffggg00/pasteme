package ru.borisof.pasteme.snippet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.borisof.pasteme.snippet.model.SyntaxType;

@Data
@Builder
@AllArgsConstructor
public class CodeSnippetDetailedDto {

  @JsonProperty("id")
  private String externalId;

  private String title;

  private String base64Content;

  private SyntaxType syntaxType;

  private LocalDateTime createdAt;

  private boolean isPublic;

  private String recipientUsername;

}
