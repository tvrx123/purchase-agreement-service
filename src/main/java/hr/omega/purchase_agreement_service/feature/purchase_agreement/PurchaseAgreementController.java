package hr.omega.purchase_agreement_service.feature.purchase_agreement;

import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.PurchaseAgreementRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.request.StatusChangeRequest;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.response.PurchaseAgreementResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase-agreements")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Kupopordajni ugovori", description = "API kupoprodajnih ugovora")
public class PurchaseAgreementController {

  private final PurchaseAgreementResourceService purchaseAgreementResourceService;

  @GetMapping("/{id}")
  @Operation(
          summary = "Dohvat po id-u ugovora",
          description = "Vraća objekt kupoprodajnog ugovora. Vraća grešku ukoliko ne postoji ugovor sa zadanim id-om.")
  public PurchaseAgreementResponse getById(@PathVariable Long id) {
    return purchaseAgreementResourceService.getDetails(id);
  }

  @GetMapping
  @Operation(
          summary = "Dohvat svih ugovora",
          description = "Vraća jednu stranicu (page) ugovora. Kao URI parametar prima objekt za paginaciju koji određuje broj stranice, njenu veličinu, i sortiranje prema nekom polju.")
  @PageableAsQueryParam
  public Page<PurchaseAgreementResponse> getAll(
      @RequestParam(required = false, name = "kupac") @Parameter(description = "Parametar za filtirarnje. Nije obavezan. Vraćaju se ugovori čije polje kupac sadrži poslani tekst (ne mora se u potpunosti poklapati).") String customerName,
      @RequestParam(required = false, name = "aktivni") @Parameter(description = "Parametar za filtirarnje. Nije obavezan. Vraćaju se aktivni ugovori ako je poslana vrijednost true, a neaktivni ako je poslana vrijednost false.")Boolean active,
      @Parameter(hidden = true) Pageable pageable) {
    return purchaseAgreementResourceService.getAllDetails(customerName, active, pageable);
  }
  @Operation(
          summary = "Uređivanje ugovora po id-u",
          description = "Vraća objekt uređenog kupoprodajnog ugovora. Vraća grešku ukoliko ne postoji ugovor sa zadanim id-om. Mjenjaju se samo parametri poslani u zahtjevu, ostali ostaju nempromijenjeni. Nije moguće uređivati obrisane ugovore.")
  @PutMapping("/{id}")
  public PurchaseAgreementResponse change(
      @PathVariable Long id,
      @RequestBody @Valid PurchaseAgreementChangeRequest purchaseAgreementChangeRequest) {
    return purchaseAgreementResourceService.change(id, purchaseAgreementChangeRequest);
  }
  @Operation(
          summary = "Promjena statusa ugovora po id-u",
          description = "Moguće su promjene isključivo iz statusa KREIRANO u NARUČENO, i iz statusa NARUČENO u ISPORUČENO.")

  @PatchMapping("/status/{id}")
  public void changeStatus(
      @PathVariable Long id, @RequestBody @Valid StatusChangeRequest statusChangeRequest) {
    purchaseAgreementResourceService.changeStatus(id, statusChangeRequest);
  }
  @Operation(
          summary = "Dodavanje ugovora",
          description = "Vraća objekt dodanog kupoprodajnog ugovora.")
  @PostMapping
  public PurchaseAgreementResponse save(
      @RequestBody @Valid PurchaseAgreementRequest purchaseAgreementRequest) {
    return purchaseAgreementResourceService.save(purchaseAgreementRequest);
  }
  @Operation(
          summary = "Brisanje ugovora po id-u",
          description = "Vraća grešku ukoliko ne postoji ugovor sa zadanim id-om.")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    purchaseAgreementResourceService.delete(id);
  }
}
