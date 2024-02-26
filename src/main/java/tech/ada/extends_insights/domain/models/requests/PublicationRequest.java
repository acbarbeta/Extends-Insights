package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.entities.Tag;

import java.util.List;

@Getter
public class PublicationRequest {
    private String title;
    private String content;
    private User user;
    private Category category;
    private List<Tag> tag;
}
