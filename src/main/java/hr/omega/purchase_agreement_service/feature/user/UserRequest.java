package hr.omega.purchase_agreement_service.feature.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
  @NotBlank private String username;
  @NotBlank private String password;
}
