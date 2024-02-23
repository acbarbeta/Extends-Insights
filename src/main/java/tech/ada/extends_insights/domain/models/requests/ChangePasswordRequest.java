package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String newPassword;

    public ChangePasswordRequest(){}

    public ChangePasswordRequest(String newPassword){
        this.newPassword = newPassword;
    }
}
