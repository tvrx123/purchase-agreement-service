package hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseAgreementItemChangeRequest {
  @JsonProperty("dobavljač")
  private String supplier;

  @Positive
  @JsonProperty("količina")
  private Integer quantity;
}
