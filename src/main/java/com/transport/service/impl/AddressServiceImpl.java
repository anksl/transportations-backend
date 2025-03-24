package com.transport.service.impl;

import com.transport.api.dto.AddressDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.AddressMapper;
import com.transport.model.Address;
import com.transport.model.City;
import com.transport.model.Country;
import com.transport.repository.AddressRepository;
import com.transport.repository.CityRepository;
import com.transport.repository.CountryRepository;
import com.transport.service.AddressService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressMapper addressMapper;

    @Override
    public List<AddressDto> getAddresses(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<AddressDto> getAddressesByCountry(Integer pageNo, Integer pageSize, String sortBy, String countryName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findByCity_Country_Name(countryName, paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<AddressDto> getAddressesByCity(Integer pageNo, Integer pageSize, String sortBy, String cityName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findByCityName(cityName, paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public AddressDto findById(Long id) {
        return addressMapper.convert(addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Address with id: %s doesn't exist", id))));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void createAddress(AddressDto addressDto) {
        Address address = addressMapper.convert(addressDto);
        var countryName = address.getCity().getCountry().getName();
        var cityName = address.getCity().getName();
        var city = checkCityAndCountryForExistence(countryName, cityName);
        address.setCity(city);
        addressRepository.save(address);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public AddressDto updateAddress(Long id, AddressDto newAddressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Address with id: %s doesn't exist", id)));
        Address newAddress = addressMapper.convert(newAddressDto);
        address.setApartment(newAddress.getApartment());
        address.setHouse(newAddress.getHouse());
        address.setStreet(newAddress.getStreet());
        address.setPhoneNumber(newAddress.getPhoneNumber());
        var countryName = newAddress.getCity().getCountry().getName();
        var cityName = newAddress.getCity().getName();
        var city = checkCityAndCountryForExistence(countryName, cityName);
        address.setCity(city);
        return addressMapper.convert(addressRepository.save(address));
    }

    private City checkCityAndCountryForExistence(String countryName, String cityName) {
        var country = countryRepository.findByName(countryName)
            .orElseGet(() -> {
                var newCountry = Country.builder().name(countryName).build();
                return countryRepository.save(newCountry);
            });

        return cityRepository.findByName(cityName)
            .orElseGet(() -> {
                var newCity = City.builder().name(cityName).country(country).build();
                return cityRepository.save(newCity);
            });
    }

    @Transactional
    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
