package tech.ada.extends_insights.domain.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.entities.Tag;

import java.util.List;

@Getter
@AllArgsConstructor
public class PublicationRequest {
    private String publicationTitle;
    private String publicationBody;
    private User author;
    private Category category;
    private List<Tag> tags;
}
