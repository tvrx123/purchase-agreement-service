package hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseAgreementItemRequest {
  @NotNull
  @JsonProperty("id_šifre_artikla")
  private Long itemId;

  @NotNull
  @JsonProperty("id_kupoprodajnog_ugovora")
  private Long purchaseAgreementId;

  @NotBlank
  @JsonProperty("dobavljač")
  private String supplier;

  @NotNull
  @Positive
  @JsonProperty("količina")
  private Integer quantity;
}
