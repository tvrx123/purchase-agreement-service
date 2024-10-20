package hr.omega.purchase_agreement_service.feature.purchase_agreement.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PurchaseAgreementSimpleResponse {
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
}
