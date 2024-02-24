package tech.ada.extends_insights.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

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
  @NotBlank(message = "This attribute is requeried!")
  @Size(min = 4, max = 20,message = "This attribute must contain at least 4 characters and a maximum of 20")
  private String username;
  @NotBlank(message = "This attribute is requeried!")
  @Size(min = 8, message = "This attribute must contain at least 8 characters")
  private String password;
  @Email
  @NotBlank(message = "This attribute requires a valid email")
  private String email;
  private LocalDateTime createdOn;
  @UpdateTimestamp
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
