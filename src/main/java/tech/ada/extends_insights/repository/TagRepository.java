package tech.ada.extends_insights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.extends_insights.domain.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
