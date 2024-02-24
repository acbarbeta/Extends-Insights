package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    private Publication publication;
}
