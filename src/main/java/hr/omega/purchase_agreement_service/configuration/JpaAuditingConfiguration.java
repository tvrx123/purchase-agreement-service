package hr.omega.purchase_agreement_service.configuration;

import hr.omega.purchase_agreement_service.feature.user.User;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
class JpaAuditingConfiguration implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    User a = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Optional.ofNullable(a == null ? null : a.getUsername());
  }
}
