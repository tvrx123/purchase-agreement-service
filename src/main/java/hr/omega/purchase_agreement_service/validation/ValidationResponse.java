package hr.omega.purchase_agreement_service.validation;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResponse {
  private int statusCode;
  private String statusType;
  private LocalDateTime timestamp;
  private String message;
  private List<String> validationList;
}
