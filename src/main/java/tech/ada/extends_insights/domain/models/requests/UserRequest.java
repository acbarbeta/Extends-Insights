package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;

@Getter
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
