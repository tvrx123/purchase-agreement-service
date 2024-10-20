package hr.omega.purchase_agreement_service.feature.purchase_agreement;

import hr.omega.purchase_agreement_service.feature.creation_audit.CreationAudit;
import hr.omega.purchase_agreement_service.feature.purchase_agreement_item.PurchaseAgreementItem;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "PURCHASE_AGREEMENTS")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseAgreement extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "CUSTOMER_NAME", nullable = false)
  private String customerName;

  @Column(name = "AGREEMENT_NUMBER", nullable = false)
  private String agreementNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @Column(name = "ADVANCE_DATE", nullable = false)
  private LocalDate advanceDate;

  @Column(name = "DELIVERY_TERM", nullable = false)
  private LocalDate deliveryTerm;

  @Column(name = "DELETED_FLAG", nullable = false)
  private Boolean deletedFlag;

  @OneToMany(mappedBy = "purchaseAgreement")
  private Set<PurchaseAgreementItem> items;

  @AllArgsConstructor
  public enum Status {
    CREATED("KREIRANO"),
    ORDERED("NARUČENO"),
    DELIVERED("ISPORUČENO");

    public final String label;

    public static Status findByLabel(String label) {
      for (Status status : Status.values()) {
        if (status.label.equals(label)) {
          return status;
        }
      }
      return null;
    }
  }
}
