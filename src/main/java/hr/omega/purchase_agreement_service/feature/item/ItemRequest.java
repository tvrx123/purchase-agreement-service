package hr.omega.purchase_agreement_service.feature.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemRequest {
  @NotBlank
  @JsonProperty("naziv")
  private String name;
}
