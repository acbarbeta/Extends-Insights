package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.enums.Tag;

import java.util.List;

@Getter
public class UpdatePublicationRequest {
    private String title;
    private String content;
    private List<Category> category;
    private List<Tag> tag;
}
