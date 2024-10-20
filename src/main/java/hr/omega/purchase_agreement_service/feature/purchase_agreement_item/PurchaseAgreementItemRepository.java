package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseAgreementItemRepository
    extends JpaRepository<PurchaseAgreementItem, Long> {

  List<PurchaseAgreementItem> findByPurchaseAgreement_id(Long purchaseAgreementId);

  @Transactional
  @Modifying
  @Query("update PurchaseAgreementItem p set p.status = ?1 where p.purchaseAgreement.id = ?2")
  void updateStatusByPurchaseAgreementId(PurchaseAgreement.Status status, Long purchaseAgreementId);
}
