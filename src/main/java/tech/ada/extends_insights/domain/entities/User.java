package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String username;
  private String password;
  private String email;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;
  @OneToMany(mappedBy = "author")
  private List<Publication> publications;
  @OneToMany(mappedBy = "author")
  private List<Comment> comments;

  public User() {
    this.createdOn = LocalDateTime.now();
    this.updatedOn = null;
  }

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.createdOn = LocalDateTime.now();
    this.updatedOn = null;
  }
}
