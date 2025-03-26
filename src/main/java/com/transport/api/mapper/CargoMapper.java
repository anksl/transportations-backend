package com.transport.api.mapper;

import com.transport.api.dto.CargoDto;
import com.transport.api.dto.SizeDto;
import com.transport.model.Cargo;
import com.transport.model.Size;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CargoMapper {

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "loadApproach", source = "loadApproach")
    @Mapping(target = "loadMethod", source = "loadMethod")
    @Mapping(target = "packaging", source = "packaging")
    @Mapping(target = "size", source = "size")
    void toCargo(@MappingTarget Cargo entity, CargoDto cargoDto);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "depth", source = "depth")
    @Mapping(target = "weight", source = "weight")
    void toSize(@MappingTarget Size entity, SizeDto cargoDto);

    Cargo convert(CargoDto cargoDto);

    CargoDto convert(Cargo cargo);

    List<CargoDto> convert(List<Cargo> cargos);
}
