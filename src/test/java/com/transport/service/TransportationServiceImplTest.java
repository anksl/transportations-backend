package com.transport.service;

import com.transport.api.dto.TransportationDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.TransportationMapper;
import com.transport.model.Transportation;
import com.transport.repository.TransportationRepository;
import com.transport.service.impl.TransportationServiceImpl;
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
public class TransportationServiceImplTest {
    @InjectMocks
    private TransportationServiceImpl transportationServiceImpl;
    @Mock
    private TransportationRepository transportationRepository;
    @Mock
    private TransportationMapper transportationMapper;
    private static final Long ID = 1L;

    @Test
    void testGetTransportations() {
        Page<Transportation> transportation = mock(Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("distance"));
        when(transportationRepository.findAll(paging)).thenReturn(transportation);
        List<TransportationDto> transportationDtos = transportationMapper.convert(transportation.getContent());
        List<TransportationDto> result = transportationServiceImpl.getTransportations(0, 1, "distance");
        assertThat(result).isEqualTo(transportationDtos);
    }

    @Test
    void testFindById() {
        final Optional<Transportation> transportation = Optional.of(TestFixture.getTransportation(ID));
        when(transportationRepository.findById(ID)).thenReturn(transportation);
        TransportationDto transportationDto = transportationMapper.convert(transportation.get());
        TransportationDto result = transportationServiceImpl.findById(ID);
        assertThat(result).isEqualTo(transportationDto);
    }

    @Test
    void testFindByIdWhenNoSuchTransportation() {
        assertThrows(NoSuchEntityException.class, () -> transportationServiceImpl.findById(2L));
    }

    @Test
    void testDelete() {
        transportationServiceImpl.deleteTransportation(ID);

        verify(transportationRepository).deleteById(ID);
    }
}
