package hr.omega.purchase_agreement_service.feature.item;

import hr.omega.purchase_agreement_service.validation.ValidationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemResourceService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;

  public Page<ItemResponse> getAllDetails(Pageable pageable) {
    return itemRepository.findAll(pageable).map(itemMapper::toResponse);
  }

  public Item findById(Long id) {
    // TODO: dodati custom exception, multijezičnost
    return this.itemRepository
        .findById(id)
        .orElseThrow(() -> new ValidationException("item.notExists"));
  }

  public ItemResponse getDetails(Long id) {
    return itemMapper.toResponse(findById(id));
  }

  public ItemResponse save(ItemRequest itemRequest) {
    Item item = itemMapper.toEntity(itemRequest);
    Optional<Item> existing = itemRepository.findByNameIgnoreCase(item.getName());
    if (existing.isPresent()) {
      throw new ValidationException("item.nameAlreadyExists");
    }
    itemRepository.save(item);
    return itemMapper.toResponse(item);
  }

  public void delete(Long id) {
    findById(id);
    itemRepository.deleteById(id);
  }

  public ItemResponse change(Long id, ItemRequest itemRequest) {
    Item item = findById(id);
    if (itemRequest.getName() != null) {
      Optional<Item> existing =
          itemRepository.findByNameIgnoreCaseAndIdNot(itemRequest.getName(), id);
      if (existing.isPresent()) {
        throw new ValidationException("item.nameAlreadyExists");
      }
    }
    itemMapper.update(itemRequest, item);
    itemRepository.save(item);
    return itemMapper.toResponse(item);
  }
}
