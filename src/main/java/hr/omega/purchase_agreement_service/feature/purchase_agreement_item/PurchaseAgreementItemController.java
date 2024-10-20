package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.request.PurchaseAgreementItemRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.response.PurchaseAgreementItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase-agreements")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Artikli ugovora", description = "API artikala kupoprodajnih ugovora")
public class PurchaseAgreementItemController {

  private final PurchaseAgreementItemResourceService purchaseAgreementItemResourceService;
  @Operation(
          summary = "Dohvat po id-u artikla",
          description = "Vraća objekt artikla. Vraća grešku ukoliko ne postoji ugovor sa zadanim id-om.")
  @GetMapping("/item/{id}")
  public PurchaseAgreementItemResponse getById(@PathVariable Long id) {
    return purchaseAgreementItemResourceService.getDetails(id);
  }
  @Operation(
          summary = "Dohvat po id-u ugovora",
          description = "Vraća listu artikala kupoprodajnog ugovora. Vraća grešku ukoliko ne postoji ugovor sa zadanim id-om.")
  @GetMapping("/{purchaseAgreementId}/items")
  public List<PurchaseAgreementItemResponse> getByPurchaseAgreementId(
      @PathVariable Long purchaseAgreementId) {
    return purchaseAgreementItemResourceService.getDetailsByPurchaseAgreementId(
        purchaseAgreementId);
  }
  @Operation(
          summary = "Uređivanje artikla po id-u",
          description = "Vraća objekt uređenog artikla. Vraća grešku ukoliko ne postoji artikl sa zadanim id-om. Mjenjaju se samo parametri poslani u zahtjevu, ostali ostaju nempromijenjeni.")
  @PutMapping("/item/{id}")
  public PurchaseAgreementItemResponse change(
      @PathVariable Long id,
      @RequestBody @Valid PurchaseAgreementItemChangeRequest purchaseAgreementItemChangeRequest) {
    return purchaseAgreementItemResourceService.change(id, purchaseAgreementItemChangeRequest);
  }
  @Operation(
          summary = "Dodavanje artikla na ugovor",
          description = "Vraća objekt dodanog artikla.")
  @PostMapping("/item")
  public PurchaseAgreementItemResponse save(
      @RequestBody @Valid PurchaseAgreementItemRequest purchaseAgreementItemRequest) {
    return purchaseAgreementItemResourceService.save(purchaseAgreementItemRequest);
  }
  @Operation(
          summary = "Brisanje artikla",
          description = "Vraća grešku ukoliko ne postoji artikl sa zadanim id-om.")
  @DeleteMapping("/item/{id}")
  public void delete(@PathVariable("id") Long id) {
    purchaseAgreementItemResourceService.delete(id);
  }
}
