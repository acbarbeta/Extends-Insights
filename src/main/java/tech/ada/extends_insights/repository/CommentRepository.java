package tech.ada.extends_insights.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.User;

import java.util.List;

@Repository
    public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAuthor(User author);
}

