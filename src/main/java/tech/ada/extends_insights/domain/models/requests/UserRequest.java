package tech.ada.extends_insights.domain.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
