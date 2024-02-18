package tech.ada.extends_insights.domain.models.requests;

import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.enums.Tag;

public class PublicationRequest {
    private String title;
    private String content;
    private User user;
    private Category category;
    private Tag tag;

    public PublicationRequest() {
    }

    public PublicationRequest(String title, String content, User user, Category category, Tag tag) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.tag = tag;
    }
}
