package hr.omega.purchase_agreement_service.feature.item;

import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemMapper {

  Item toEntity(ItemRequest itemRequest);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(ItemRequest itemRequest, @MappingTarget Item target);

  ItemResponse toResponse(Item item);

  List<ItemResponse> toResponse(List<Item> items);
}
