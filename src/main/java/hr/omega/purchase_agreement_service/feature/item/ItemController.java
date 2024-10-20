package hr.omega.purchase_agreement_service.feature.item;

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
@RequestMapping("items")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Šifrarnik artikala", description = "API šifrarnika artikala")
public class ItemController {

  private final ItemResourceService itemResourceService;
  @Operation(
          summary = "Dohvat po id-u šifriranog artikla",
          description = "Vraća objekt šifriranog artikla. Vraća grešku ukoliko ne postoji šifrirani artikl sa zadanim id-om.")
  @GetMapping("/{id}")
  public ItemResponse getById(@PathVariable Long id) {
    return itemResourceService.getDetails(id);
  }
  @Operation(
          summary = "Dohvat svih šifriranih artikala",
          description = "Vraća jednu stranicu (page) šifrarnika. Kao URI parametar prima objekt za paginaciju koji određuje broj stranice, njenu veličinu, i sortiranje prema nekom polju.")
  @GetMapping
  @PageableAsQueryParam
  public Page<ItemResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
    return itemResourceService.getAllDetails(pageable);
  }
  @Operation(
          summary = "Uređivanje šifriranog artikla po id-u",
          description = "Vraća objekt uređenog šifriranog artikla. Vraća grešku ukoliko ne postoji šifrirani artikl sa zadanim id-om.")
  @PutMapping("/{id}")
  public ItemResponse change(@PathVariable Long id, @RequestBody @Valid ItemRequest itemRequest) {
    return itemResourceService.change(id, itemRequest);
  }
  @Operation(
          summary = "Dodavanje šifriranog artikla",
          description = "Vraća objekt dodanog šifriranog artikla. Vraća grešku ukoliko već postoji šifrirani artikl sa zadanim nazivom.")

  @PostMapping
  public ItemResponse save(@RequestBody @Valid ItemRequest itemRequest) {
    return itemResourceService.save(itemRequest);
  }
  @Operation(
          summary = "Brisanje šifriranog artikla po id-u",
          description = "Vraća grešku ukoliko ne postoji šifrirani artikl sa zadanim id-om.")

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    itemResourceService.delete(id);
  }
}
