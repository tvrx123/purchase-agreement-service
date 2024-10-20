package hr.omega.purchase_agreement_service.feature.purchase_agreement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PurchaseAgreementRequest {
  @NotBlank
  @JsonProperty("kupac")
  private String customerName;

  @NotNull
  @JsonProperty("datum_akontacije")
  private LocalDate advanceDate;

  @NotNull
  @Schema(description = "Mora biti nakon datuma akontacije.")
  @JsonProperty("rok_isporuke")
  private LocalDate deliveryTerm;
}
