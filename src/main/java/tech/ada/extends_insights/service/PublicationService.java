package tech.ada.extends_insights.service;

import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;

import java.util.List;

public interface PublicationService {
    Publication createPublication(PublicationRequest request);
    List<Publication> readAllPublications();
    Publication readPublicationById(Long id);
    List<Publication> readPublicationByTitle(String title);
    List<Publication> getPublicationByCategory(Category category);
    List<Publication> getPublicationByTag(Tag tag);
    List<Publication> getPublicationByUser(Long userId);
    Publication updatePublication(Long id, UpdatePublicationRequest updatePublicationRequest);
    void deletePublication(Long id);
}
