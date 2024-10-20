package hr.omega.purchase_agreement_service.feature.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemResponse {
  private Long id;

  @JsonProperty("naziv")
  private String name;
}
