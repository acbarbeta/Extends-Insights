package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

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
    @ManyToOne
    private User author;
    private Long authorId;
    @ManyToOne
    private Publication publication;
    private String commentBody;
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public Comment(){
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }

    public Comment(Long commentId,
                   User author,
                   Publication publication,
                   String commentBody){
        this.commentId = commentId;
        this.author = author;
        this.authorId = author.getUserId();
        this.publication = publication;
        this.commentBody = commentBody;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }
}
