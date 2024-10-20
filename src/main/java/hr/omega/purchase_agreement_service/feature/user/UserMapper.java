package hr.omega.purchase_agreement_service.feature.user;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User toEntity(UserRequest userRequest);
}
