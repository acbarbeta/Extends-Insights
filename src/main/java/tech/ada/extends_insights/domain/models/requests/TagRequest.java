package tech.ada.extends_insights.domain.models.requests;

import tech.ada.extends_insights.domain.entities.Publication;

public class TagRequest {
    private String title;
    private Publication publication;

    public TagRequest() {

    }

    public TagRequest(String title, Publication publication) {
        this.title = title;
        this.publication = publication;
    }
}
