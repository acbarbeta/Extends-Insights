package tech.ada.extends_insights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.extends_insights.domain.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>{

}
