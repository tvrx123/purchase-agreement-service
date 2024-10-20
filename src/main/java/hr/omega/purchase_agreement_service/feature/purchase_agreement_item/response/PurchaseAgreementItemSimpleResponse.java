package hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PurchaseAgreementItemSimpleResponse {
  private Long id;

  @JsonProperty("naziv")
  private String name;

  @JsonProperty("dobavljač")
  private String supplier;

  @JsonProperty("količina")
  private Integer quantity;

  private String status;
}
