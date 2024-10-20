package hr.omega.purchase_agreement_service.feature.purchase_agreement_item;

import hr.omega.purchase_agreement_service.feature.creation_audit.CreationAudit;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "PURCHASE_AGREEMENT_ITEMS")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseAgreementItem extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "SUPPLIER", nullable = false)
  private String supplier;

  @Column(name = "QUANTITY", nullable = false)
  private Integer quantity;

  // TODO: U primjeru JSON svakog artikla ima poseban status, pošto nije specificirano da se
  // postavlja kroz api, bit ce isti kao i ugovor, moguće proširenje je svaki artikl posebno
  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private PurchaseAgreement.Status status;

  @ManyToOne
  @JoinColumn(name = "PURCHASE_AGREEMENT_ID")
  private PurchaseAgreement purchaseAgreement;
}
