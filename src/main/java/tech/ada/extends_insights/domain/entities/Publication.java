package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.enums.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "publications")
@Setter
@Getter
@EqualsAndHashCode
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicationId;
    private String publicationTitle;
    private String publicationBody;
    @ManyToOne
    private User author;
    private List<Category> categories;
    private List<Tag> tags;
    private Integer views;
    @OneToMany(mappedBy = "publication")
    private List<Comment> comments;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Publication(){
        this.views = 0;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }

    public Publication(String publicationTitle,
                       String publicationBody,
                       User author,
                       List<Category> category,
                       List<Tag> tags){
        this.publicationTitle = publicationTitle;
        this.publicationBody = publicationBody;
        this.author = author;
        this.categories = category;
        this.tags = tags;
        this.views = 0;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }
}