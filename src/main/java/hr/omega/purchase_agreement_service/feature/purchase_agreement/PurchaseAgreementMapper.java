package hr.omega.purchase_agreement_service.feature.purchase_agreement;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementResponse;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementSimpleResponse;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.PurchaseAgreementItemMapper;
import java.util.List;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {PurchaseAgreementItemMapper.class})
public interface PurchaseAgreementMapper {

  @Named("getStatusLabel")
  static String geStatusLabel(PurchaseAgreement.Status status) {
    return status.label;
  }

  @Named("getStatusByLabel")
  static PurchaseAgreement.Status geStatusByLabel(String status) {
    return PurchaseAgreement.Status.findByLabel(status);
  }

  @Mapping(target = "deletedFlag", constant = "false")
  PurchaseAgreement toEntity(PurchaseAgreementRequest purchaseAgreementRequest);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(
      PurchaseAgreementChangeRequest purchaseAgreementRequest,
      @MappingTarget PurchaseAgreement target);

  @Mapping(target = "status", source = "status", qualifiedByName = "getStatusLabel")
  PurchaseAgreementResponse toResponse(PurchaseAgreement purchaseAgreement);

  @Mapping(target = "status", source = "status", qualifiedByName = "getStatusLabel")
  List<PurchaseAgreementResponse> toResponse(List<PurchaseAgreement> purchaseAgreements);

  @Mapping(target = "status", source = "status", qualifiedByName = "getStatusLabel")
  PurchaseAgreementSimpleResponse toSimpleResponse(PurchaseAgreement purchaseAgreements);
}
