package hr.omega.purchase_agreement_service.feature.purchase_agreement.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemSimpleResponse;
import java.time.LocalDate;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PurchaseAgreementResponse {
  private Long id;
  @JsonProperty("kupac")
  private String customerName;

  @JsonProperty("broj_ugovora")
  private String agreementNumber;

  private String status;

  @JsonProperty("datum_akontacije")
  private LocalDate advanceDate;

  @JsonProperty("rok_isporuke")
  private LocalDate deliveryTerm;

  @JsonProperty("artikli")
  private Set<PurchaseAgreementItemSimpleResponse> items;
}
