package com.transport.service.impl;

import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.UserDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.PaymentMapper;
import com.transport.api.mapper.UserMapper;
import com.transport.model.Email;
import com.transport.model.Payment;
import com.transport.model.User;
import com.transport.model.enums.PaymentStatus;
import com.transport.repository.PaymentRepository;
import com.transport.service.PaymentService;
import com.transport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional
    @Override
    public PaymentDto updatePayment(Long id, PaymentDto newPaymentDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Payment with id: %s doesn't exist", id)));
        Payment newPayment = paymentMapper.convert(newPaymentDto);
        payment.setPrice(newPayment.getPrice());
        payment.setDate(newPayment.getDate());
        payment.setDeadline(newPayment.getDeadline());
        setPaymentStatus(payment);
        payment.setUser(newPayment.getUser());
        return paymentMapper.convert(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentDto> getPayments(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Payment> pagedResult = paymentRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return paymentMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PaymentDto> getForCurrentUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        UserDto user = userService.getCurrentUser();
        Page<Payment> pagedResult = paymentRepository.findByUser(userMapper.convert(user), paging);
        if (pagedResult.hasContent()) {
            return paymentMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PaymentDto> findByPriceGreaterThan(BigDecimal price, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Payment> pagedResult = paymentRepository.findByPriceGreaterThan(price, paging);
        if (pagedResult.hasContent()) {
            return paymentMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Email remindDebtors() {
        Email email = new Email();
        String[] recipients = paymentRepository.findByPaymentStatusOWE().stream().map(User::getEmail).toArray(String[]::new);
        email.setRecipients(recipients);
        email.setSubject("Transport.com");
        email.setMsgBody("Reminder about the presence of unpaid transportations!");
        return email;
    }

    @Override
    public void setPaymentStatus(Payment payment) {
        if (payment.getDate() == null) {
            payment.setPaymentStatus(PaymentStatus.OWE);
        } else if (payment.getDeadline().after(payment.getDate())) {
            payment.setPaymentStatus(PaymentStatus.ON_TIME);
        } else {
            payment.setPaymentStatus(PaymentStatus.LATE);
        }
    }
}
