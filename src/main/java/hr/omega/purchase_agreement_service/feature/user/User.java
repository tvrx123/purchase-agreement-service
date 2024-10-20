package hr.omega.purchase_agreement_service.feature.user;

import hr.omega.purchase_agreement_service.feature.creation_audit.CreationAudit;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Table(name = "USERS")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User extends CreationAudit implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "USERNAME", nullable = false)
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }
}
