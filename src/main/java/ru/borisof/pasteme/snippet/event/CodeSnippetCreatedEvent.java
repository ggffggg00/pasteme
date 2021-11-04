package ru.borisof.pasteme.snippet.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class CodeSnippetCreatedEvent extends ApplicationEvent {

  private long snippetId;

  private long recipientId;

  public CodeSnippetCreatedEvent(long snippetId, long recipientId) {
    super(snippetId);
    this.snippetId = snippetId;
    this.recipientId = recipientId;
  }
}
