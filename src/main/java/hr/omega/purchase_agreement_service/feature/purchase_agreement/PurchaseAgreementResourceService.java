package hr.omega.purchase_agreement_service.feature.purchase_agreement;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.repository.PurchaseAgreementQueryDslRepository;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.repository.PurchaseAgreementRepository;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.StatusChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementResponse;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.PurchaseAgreementItemResourceService;
import hr.omega.purchase_agreement_service.validation.ValidationException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseAgreementResourceService {

  private final PurchaseAgreementRepository purchaseAgreementRepository;
  private final PurchaseAgreementMapper purchaseAgreementMapper;
  private final PurchaseAgreementQueryDslRepository purchaseAgreementQueryDslRepository;
  private PurchaseAgreementItemResourceService purchaseAgreementItemResourceService;

  public Page<PurchaseAgreementResponse> getAllDetails(
      String customerName, Boolean active, Pageable pageable) {
    return purchaseAgreementQueryDslRepository
        .findPageFiltered(customerName, active, pageable)
        .map(purchaseAgreementMapper::toResponse);
  }

  public PurchaseAgreement findById(Long id) {
    return this.purchaseAgreementRepository
        .findById(id)
        .orElseThrow(() -> new ValidationException("purchaseAgreement.notExists"));
  }

  public PurchaseAgreementResponse getDetails(Long id) {
    return purchaseAgreementMapper.toResponse(findById(id));
  }

  public PurchaseAgreementResponse save(PurchaseAgreementRequest purchaseAgreementRequest) {
    PurchaseAgreement purchaseAgreement =
        purchaseAgreementMapper.toEntity(purchaseAgreementRequest);
    purchaseAgreement.setStatus(PurchaseAgreement.Status.CREATED);
    purchaseAgreement.setAgreementNumber(getNextAgreementNumber());
    validateAdvanceDateBeforeDeliveryTerm(purchaseAgreement);
    purchaseAgreementRepository.save(purchaseAgreement);
    return purchaseAgreementMapper.toResponse(purchaseAgreement);
  }

  public void delete(Long id) {
    PurchaseAgreement purchaseAgreement = findById(id);
    if (PurchaseAgreement.Status.CREATED != purchaseAgreement.getStatus())
      throw new ValidationException("purchaseAgreement.deletionOnlyCreated");
    purchaseAgreement.setDeletedFlag(true);
    purchaseAgreementRepository.save(purchaseAgreement);
  }

  public PurchaseAgreementResponse change(
      Long id, PurchaseAgreementChangeRequest purchaseAgreementChangeRequest) {
    PurchaseAgreement purchaseAgreement = findById(id);
    validatePurchaseAgreementNotDeleted(purchaseAgreement);
    purchaseAgreementMapper.update(purchaseAgreementChangeRequest, purchaseAgreement);
    validateStatusForDeliveryTermChange(purchaseAgreement, purchaseAgreementChangeRequest);
    validateAdvanceDateBeforeDeliveryTerm(purchaseAgreement);
    purchaseAgreementRepository.save(purchaseAgreement);
    return purchaseAgreementMapper.toResponse(purchaseAgreement);
  }

  public void changeStatus(Long id, StatusChangeRequest statusChangeRequest) {
    PurchaseAgreement purchaseAgreement = findById(id);
    PurchaseAgreement.Status newStatus =
        PurchaseAgreement.Status.findByLabel(statusChangeRequest.getStatus());
    switch (newStatus) {
      case null -> throw new ValidationException("purchaseAgreement.statusNotValid");
      case PurchaseAgreement.Status.CREATED ->
          throw new ValidationException("purchaseAgreement.statusChangeToCreatedNotAllowed");
      case PurchaseAgreement.Status.ORDERED -> {
        if (purchaseAgreement.getStatus() != PurchaseAgreement.Status.CREATED)
          throw new ValidationException("purchaseAgreement.statusChangeToOrderedNotAllowed");
      }
      case PurchaseAgreement.Status.DELIVERED -> {
        if (purchaseAgreement.getStatus() != PurchaseAgreement.Status.ORDERED)
          throw new ValidationException("purchaseAgreement.statusChangeToDeliveredNotAllowed");
      }
    }
    if (purchaseAgreement.getItems() == null || purchaseAgreement.getItems().isEmpty()) {
      throw new ValidationException("purchaseAgreement.statusChangeNoItems");
    }
    purchaseAgreement.setStatus(newStatus);
    purchaseAgreementRepository.save(purchaseAgreement);
    purchaseAgreementItemResourceService.changeStatusByPurchaseAgreement(purchaseAgreement);
  }

  private void validateStatusForDeliveryTermChange(
      PurchaseAgreement purchaseAgreement,
      PurchaseAgreementChangeRequest purchaseAgreementChangeRequest) {
    if (purchaseAgreementChangeRequest.getDeliveryTerm() != null
        && purchaseAgreement.getStatus() != PurchaseAgreement.Status.ORDERED
        && purchaseAgreement.getStatus() != PurchaseAgreement.Status.CREATED) {
      throw new ValidationException("purchaseAgreement.deliveryDateChangeOnlyActive");
    }
  }

  private void validateAdvanceDateBeforeDeliveryTerm(PurchaseAgreement purchaseAgreement) {
    if (!purchaseAgreement.getAdvanceDate().isBefore(purchaseAgreement.getDeliveryTerm())) {
      throw new ValidationException("purchaseAgreement.advanceBeforeDelivery");
    }
  }

  public void validatePurchaseAgreementNotDeleted(PurchaseAgreement purchaseAgreement) {
    if (purchaseAgreement.getDeletedFlag()) {
      throw new ValidationException("purchaseAgreement.deleted");
    }
  }

  private String getNextAgreementNumber() {
    Long nextSequenceValue = purchaseAgreementRepository.getNextAgreementNumber();
    return nextSequenceValue + "/" + LocalDate.now().getYear();
  }

  @Autowired
  @Lazy
  public void setPurchaseAgreementItemResourceService(
      PurchaseAgreementItemResourceService purchaseAgreementItemResourceService) {
    this.purchaseAgreementItemResourceService = purchaseAgreementItemResourceService;
  }
}
