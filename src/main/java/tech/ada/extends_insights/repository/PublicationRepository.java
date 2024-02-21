package tech.ada.extends_insights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.entities.Tag;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>{
    public List<Publication> findByPublicationTitle(String title);
    public List<Publication> findByCategory(Category category);
    public List<Publication> findByTags(Tag tag);
    public List<Publication> findByAuthor(User author);
}
