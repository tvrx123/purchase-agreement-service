package hr.omega.purchase_agreement_service.feature.user;

import lombok.Data;

@Data
public class LoginResponse {
  private String token;
  private Long expiresIn;
}
