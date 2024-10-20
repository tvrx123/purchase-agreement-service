package hr.omega.purchase_agreement_service.feature.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Autentikacija", description = "API za autentikaciju")
public class UserController {

  private final UserResourceService userResourceService;

  @PostMapping("/register")
  @Operation(
          summary = "Registracija korisnika",
          description = "Vraća grešku ukoliko već postoji korisnik sa zadanim username-om.")
  public void register(@RequestBody @Valid UserRequest userRequest) {
    userResourceService.register(userRequest);
  }

  @PostMapping("/login")
  @Operation(
          summary = "Prijava korisnika",
          description = "Vraća JWT token prijavljenog korisnika.")
  public LoginResponse login(@RequestBody @Valid UserRequest userRequest) {
    return userResourceService.login(userRequest);
  }
}
