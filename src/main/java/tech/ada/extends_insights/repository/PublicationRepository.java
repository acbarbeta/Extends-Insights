package tech.ada.extends_insights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.enums.Category;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>{
    List<Publication> findByPublicationTitle(String title);
    List<Publication> findByCategory(Category category);
    List<Publication> findByTags(Tag tag);
    List<Publication> findByAuthor(Long userId);
}
