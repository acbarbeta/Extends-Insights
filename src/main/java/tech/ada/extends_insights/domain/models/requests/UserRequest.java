package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;

import java.time.LocalDateTime;

@Getter
public class UserRequest {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public User toEntity(){
        return new User(username, email, password);
    }
}
