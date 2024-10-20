package hr.omega.purchase_agreement_service.feature.item;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  Optional<Item> findByNameIgnoreCase(String name);

  Optional<Item> findByNameIgnoreCaseAndIdNot(String name, Long id);
}
