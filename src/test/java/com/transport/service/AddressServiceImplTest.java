package com.transport.service;

import com.transport.api.dto.AddressDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.AddressMapper;
import com.transport.model.Address;
import com.transport.repository.AddressRepository;
import com.transport.service.impl.AddressServiceImpl;
import com.transport.utils.TestFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    @InjectMocks
    private AddressServiceImpl addressServiceImpl;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    private static final Long ID = 1L;

    @Test
    void testGetAddresses() {
        Page<Address> address = mock(Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("city"));
        when(addressRepository.findAll(paging)).thenReturn(address);
        List<AddressDto> addressDtos = addressMapper.convert(address.getContent());
        List<AddressDto> result = addressServiceImpl.getAddresses(0, 1, "city");
        assertThat(result).isEqualTo(addressDtos);
    }

    @Test
    void testFindById() {
        final Optional<Address> address = Optional.of(TestFixture.getAddress(ID));
        when(addressRepository.findById(ID)).thenReturn(address);
        AddressDto addressDto = addressMapper.convert(address.get());
        AddressDto result = addressServiceImpl.findById(ID);
        assertThat(result).isEqualTo(addressDto);
    }

    @Test
    void testFindByIdWhenNoSuchUser() {
        assertThrows(NoSuchEntityException.class, () -> addressServiceImpl.findById(2L));
    }
}
