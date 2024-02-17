package tech.ada.extends_insights;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;
  private String username;
  private String password;
  private String email;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

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
