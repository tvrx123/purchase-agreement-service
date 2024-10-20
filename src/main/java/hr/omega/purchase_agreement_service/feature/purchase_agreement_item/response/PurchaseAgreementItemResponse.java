package hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementSimpleResponse;
import lombok.Data;

@Data
public class PurchaseAgreementItemResponse {

  private Long id;

  @JsonProperty("naziv")
  private String name;

  @JsonProperty("dobavljač")
  private String supplier;

  @JsonProperty("količina")
  private Integer quantity;

  private String status;

  @JsonProperty("kupoprodajni_ugovor")
  private PurchaseAgreementSimpleResponse purchaseAgreement;
}
