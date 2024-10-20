package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemResponse;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemSimpleResponse;
import java.util.List;
import java.util.Set;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseAgreementItemMapper {

  @Named("getItemStatusLabel")
  static String geStatusLabel(PurchaseAgreement.Status status) {
    return status.label;
  }

  PurchaseAgreementItem toEntity(PurchaseAgreementItemRequest purchaseAgreementItemRequest);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(
      PurchaseAgreementItemChangeRequest purchaseAgreementItemRequest,
      @MappingTarget PurchaseAgreementItem target);

  @Mapping(target = "status", source = "status", qualifiedByName = "getItemStatusLabel")
  @Mapping(
      target = "purchaseAgreement.status",
      source = "purchaseAgreement.status",
      qualifiedByName = "getItemStatusLabel")
  PurchaseAgreementItemResponse toResponse(PurchaseAgreementItem purchaseAgreementItem);

  @Mapping(target = "status", source = "status", qualifiedByName = "getItemStatusLabel")
  @Mapping(
      target = "purchaseAgreement.status",
      source = "purchaseAgreement.status",
      qualifiedByName = "getItemStatusLabel")
  List<PurchaseAgreementItemResponse> toResponse(
      List<PurchaseAgreementItem> purchaseAgreementItems);

  @Mapping(target = "status", source = "status", qualifiedByName = "getItemStatusLabel")
  PurchaseAgreementItemSimpleResponse toSimpleResponse(
      PurchaseAgreementItem purchaseAgreementItems);

  @Mapping(target = "status", source = "status", qualifiedByName = "getItemStatusLabel")
  Set<PurchaseAgreementItemSimpleResponse> toSimpleResponse(
      Set<PurchaseAgreementItem> purchaseAgreementItems);
}
