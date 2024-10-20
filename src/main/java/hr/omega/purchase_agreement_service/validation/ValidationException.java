package hr.omega.purchase_agreement_service.validation;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
