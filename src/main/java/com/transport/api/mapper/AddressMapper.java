package com.transport.api.mapper;

import com.transport.api.dto.AddressDto;
import com.transport.model.Address;
import org.mapstruct.Mapper;

import java.util.List;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "city.name", source = "cityName")
    @Mapping(target = "city.country.name", source = "countryName")
    Address convert(AddressDto addressDto);

    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "countryName", source = "city.country.name")
    AddressDto convert(Address address);

    List<AddressDto> convert(List<Address> addresses);
}
