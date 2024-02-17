package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Setter
@Getter
@EqualsAndHashCode
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private User user;
    private Publication publication;
    private String commentBody;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Comment(){
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }

    public Comment(User user,
                   Publication publication,
                   String commentBody,
                   LocalDateTime createdOn,
                   LocalDateTime updatedOn){
        this.user = user;
        this.publication = publication;
        this.commentBody = commentBody;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }
}
