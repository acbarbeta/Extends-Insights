package tech.ada.extends_insights.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private User author;
    private List<Category> category;
    private List<Tag> tags;
    private Integer views;
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
        this.category = category;
        this.tags = tags;
        this.views = 0;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }
}