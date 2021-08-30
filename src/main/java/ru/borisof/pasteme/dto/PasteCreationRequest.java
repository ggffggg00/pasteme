package ru.borisof.pasteme.dto;

import lombok.Builder;
import lombok.Data;
import ru.borisof.pasteme.dao.Paste;

@Data
@Builder
public class PasteCreationRequest {

    private String title = "";

    private String syntaxType = Paste.PasteSyntaxType.NONE.name();

    private String content;

}
