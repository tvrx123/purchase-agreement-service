package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import hr.omega.purchase_agreement_service.feature.item.Item;
import hr.omega.purchase_agreement_service.feature.item.ItemResourceService;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreementResourceService;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemResponse;
import hr.omega.purchase_agreement_service.validation.ValidationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseAgreementItemResourceService {

  private final PurchaseAgreementItemRepository purchaseAgreementItemRepository;
  private final PurchaseAgreementItemMapper purchaseAgreementItemMapper;
  private final ItemResourceService itemResourceService;
  private PurchaseAgreementResourceService purchaseAgreementResourceService;

  public List<PurchaseAgreementItemResponse> getDetailsByPurchaseAgreementId(
      Long purchaseAgreementId) {
    return purchaseAgreementItemMapper.toResponse(
        purchaseAgreementItemRepository.findByPurchaseAgreement_id(purchaseAgreementId));
  }

  public PurchaseAgreementItem findById(Long id) {
    return this.purchaseAgreementItemRepository
        .findById(id)
        .orElseThrow(() -> new ValidationException("purchaseAgreementItem.notExists"));
  }

  public PurchaseAgreementItemResponse getDetails(Long id) {
    return purchaseAgreementItemMapper.toResponse(findById(id));
  }

  public PurchaseAgreementItemResponse save(
      PurchaseAgreementItemRequest purchaseAgreementItemRequest) {
    PurchaseAgreementItem purchaseAgreementItem =
        purchaseAgreementItemMapper.toEntity(purchaseAgreementItemRequest);
    Item item = itemResourceService.findById(purchaseAgreementItemRequest.getItemId());
    purchaseAgreementItem.setName(item.getName());
    PurchaseAgreement purchaseAgreement =
        purchaseAgreementResourceService.findById(
            purchaseAgreementItemRequest.getPurchaseAgreementId());
    validatePurchaseAgreementStatus(purchaseAgreement);
    purchaseAgreementResourceService.validatePurchaseAgreementNotDeleted(purchaseAgreement);
    purchaseAgreementItem.setPurchaseAgreement(purchaseAgreement);
    purchaseAgreementItem.setStatus(PurchaseAgreement.Status.CREATED);
    purchaseAgreementItemRepository.save(purchaseAgreementItem);
    return purchaseAgreementItemMapper.toResponse(purchaseAgreementItem);
  }

  public void delete(Long id) {
    PurchaseAgreementItem item = findById(id);
    validatePurchaseAgreementStatus(item.getPurchaseAgreement());
    purchaseAgreementResourceService.validatePurchaseAgreementNotDeleted(
        item.getPurchaseAgreement());
    purchaseAgreementItemRepository.deleteById(id);
  }

  public PurchaseAgreementItemResponse change(
      Long id, PurchaseAgreementItemChangeRequest purchaseAgreementItemChangeRequest) {
    PurchaseAgreementItem item = findById(id);
    validatePurchaseAgreementStatus(item.getPurchaseAgreement());
    purchaseAgreementResourceService.validatePurchaseAgreementNotDeleted(
        item.getPurchaseAgreement());
    purchaseAgreementItemMapper.update(purchaseAgreementItemChangeRequest, item);
    purchaseAgreementItemRepository.save(item);
    return purchaseAgreementItemMapper.toResponse(item);
  }

  private void validatePurchaseAgreementStatus(PurchaseAgreement purchaseAgreement) {
    if (purchaseAgreement.getStatus() != PurchaseAgreement.Status.CREATED) {
      throw new ValidationException("purchaseAgreementItem.modificationOnlyCreatedAgreement");
    }
  }

  @Autowired
  public void setPurchaseAgreementResourceService(
      PurchaseAgreementResourceService purchaseAgreementResourceService) {
    this.purchaseAgreementResourceService = purchaseAgreementResourceService;
  }

  public void changeStatusByPurchaseAgreement(PurchaseAgreement purchaseAgreement) {
    purchaseAgreementItemRepository.updateStatusByPurchaseAgreementId(
        purchaseAgreement.getStatus(), purchaseAgreement.getId());
  }
}
