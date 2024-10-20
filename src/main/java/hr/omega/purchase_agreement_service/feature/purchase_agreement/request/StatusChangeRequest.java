package hr.omega.purchase_agreement_service.feature.purchase_agreement.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusChangeRequest {
  @NotBlank private String status;
}
