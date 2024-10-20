package hr.omega.purchase_agreement_service.feature.purchase_agreement.repository;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseAgreementRepository extends JpaRepository<PurchaseAgreement, Long> {
  @Query(value = "select nextval('PURCHASE_AGREEMENT_NUMBER_SEQ')", nativeQuery = true)
  Long getNextAgreementNumber();
}
