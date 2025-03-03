package com.transport.api.mapper;

import com.transport.api.dto.user.RoleDto;
import com.transport.model.Role;
import com.transport.model.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "role", qualifiedByName = "roleToId")
    Role toEntity(RoleDto roleDto);

    @Mapping(target = "role", source = "id", qualifiedByName = "idToRole")
    RoleDto toDto(Role role);

    @Named("roleToId")
    default Long mapRoleToId(String role) {
        return RoleType.valueOf(role).getId().longValue();
    }

    @Named("idToRole")
    default String mapIdToRole(Long id) {
        RoleType roleType = RoleType.get(id.intValue());
        return roleType != null ? roleType.name() : null;
    }
}
