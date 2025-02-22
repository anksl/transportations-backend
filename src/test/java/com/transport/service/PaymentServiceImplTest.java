package com.transport.service;

import com.transport.api.dto.PaymentDto;
import com.transport.api.mapper.PaymentMapper;
import com.transport.model.Payment;
import com.transport.repository.PaymentRepository;
import com.transport.service.impl.PaymentServiceImpl;
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

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;

    private static final Long ID = 1L;

    @Test
    void testGetPayments() {
        Page<Payment> payments = mock(Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("date"));
        when(paymentRepository.findAll(paging)).thenReturn(payments);
        List<PaymentDto> addressDtos = paymentMapper.convert(payments.getContent());
        List<PaymentDto> result = paymentServiceImpl.getPayments(0, 1, "date");
        assertThat(result).isEqualTo(addressDtos);
    }

    @Test
    void testFindPaymentsByPriceMoreThan() {
        Page<Payment> payments = mock(Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("date"));
        when(paymentRepository.findByPriceGreaterThan(BigDecimal.valueOf(100.00), paging)).thenReturn(payments);
        List<PaymentDto> paymentDtos = paymentMapper.convert(payments.getContent());
        List<PaymentDto> result = paymentServiceImpl.findByPriceGreaterThan(BigDecimal.valueOf(100.00), 0, 1, "date");
        assertThat(result).isEqualTo(paymentDtos);
    }

    @Test
    void testFindPaymentWithUsersByDate() {
        Page<Payment> payments = mock(Page.class);
        Pageable paging = PageRequest.of(0, 1, Sort.by("date"));
        lenient().when(paymentRepository.findByUser(TestFixture.getUser(ID), paging)).thenReturn(payments);
        assertThrows(NullPointerException.class, () -> paymentServiceImpl.getForCurrentUser(0, 1, "date"));
    }
}
