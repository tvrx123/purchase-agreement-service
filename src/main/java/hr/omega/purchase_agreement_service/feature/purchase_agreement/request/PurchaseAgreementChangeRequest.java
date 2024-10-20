package hr.omega.purchase_agreement_service.feature.purchase_agreement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PurchaseAgreementChangeRequest {
  @JsonProperty("kupac")
  private String customerName;

  @JsonProperty("datum_akontacije")
  private LocalDate advanceDate;

  @JsonProperty("rok_isporuke")
  @Schema(description = "Moguće je mijenjati samo kod aktivnih ugovora. Mora biti nakon datuma akontacije.")
  private LocalDate deliveryTerm;
}
