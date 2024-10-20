package hr.omega.purchase_agreement_service.feature.purchase_agreement.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.PurchaseAgreement;
import hr.omega.purchase_agreement_service.feature.purchase_agreement.QPurchaseAgreement;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseAgreementQueryDslRepository {

  private final EntityManager entityManager;

  public Page<PurchaseAgreement> findPageFiltered(
      String customerName, Boolean active, Pageable pageable) {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    Querydsl querydsl =
        new Querydsl(entityManager, (new PathBuilderFactory()).create(PurchaseAgreement.class));
    QPurchaseAgreement purchaseAgreement = QPurchaseAgreement.purchaseAgreement;
    BooleanExpression predicate = purchaseAgreement.deletedFlag.eq(false);
    if (customerName != null) {
      predicate =
          predicate.and(purchaseAgreement.customerName.likeIgnoreCase("%" + customerName + "%"));
    }
    if (active != null) {
      if (active)
        predicate =
            predicate.and(
                purchaseAgreement
                    .status
                    .eq(PurchaseAgreement.Status.CREATED)
                    .or(purchaseAgreement.status.eq(PurchaseAgreement.Status.ORDERED)));
      else
        predicate = predicate.and(purchaseAgreement.status.eq(PurchaseAgreement.Status.DELIVERED));
    }
    JPQLQuery<PurchaseAgreement> query =
        queryFactory.selectFrom(purchaseAgreement).where(predicate);
    Long total = query.fetchCount();
    List<PurchaseAgreement> result = querydsl.applyPagination(pageable, query).fetch();
    return new PageImpl<PurchaseAgreement>(result, pageable, total);
  }
}
