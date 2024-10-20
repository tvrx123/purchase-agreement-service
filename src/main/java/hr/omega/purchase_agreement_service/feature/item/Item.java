package hr.omega.purchase_agreement_service.feature.item;

import hr.omega.purchase_agreement_service.feature.creation_audit.CreationAudit;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "ITEMS")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;
}
