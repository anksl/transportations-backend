package com.transport.service;

import com.transport.api.dto.CargoDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.CargoMapper;
import com.transport.model.Cargo;
import com.transport.repository.CargoRepository;
import com.transport.service.impl.CargoServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoServiceImplTest {
    @InjectMocks
    private CargoServiceImpl cargoServiceImpl;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private CargoMapper cargoMapper;
    private static final Long ID = 1L;

    @Test
    void testGetCargos() {
        Page<Cargo> cargo = mock(org.springframework.data.domain.Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("name"));
        when(cargoRepository.findAll(paging)).thenReturn(cargo);
        List<CargoDto> addressDtos = cargoMapper.convert(cargo.getContent());
        List<CargoDto> result = cargoServiceImpl.getCargos(0, 1, "name");
        assertThat(result).isEqualTo(addressDtos);
    }

    @Test
    void testFindById() {
        final Optional<Cargo> cargo = Optional.of(TestFixture.getCargo(ID));
        when(cargoRepository.findById(ID)).thenReturn(cargo);
        CargoDto cargoDto = cargoMapper.convert(cargo.get());
        CargoDto result = cargoServiceImpl.findById(ID);
        assertThat(result).isEqualTo(cargoDto);
    }

    @Test
    void testFindByIdWhenNoSuchCargo() {
        assertThrows(NoSuchEntityException.class, () -> cargoServiceImpl.findById(2L));
    }

    @Test
    void testDelete() {
        cargoServiceImpl.deleteCargo(ID);

        verify(cargoRepository).deleteById(ID);
    }
}
