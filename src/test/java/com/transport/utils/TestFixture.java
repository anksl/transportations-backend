package com.transport.utils;

import com.transport.api.dto.CargoDto;
import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.SecureUserDto;
import com.transport.api.dto.SizeDto;
import com.transport.api.dto.TransportationDto;
import com.transport.api.dto.UserDto;
import com.transport.model.Address;
import com.transport.model.Cargo;
import com.transport.model.Payment;
import com.transport.model.Size;
import com.transport.model.Transportation;
import com.transport.model.User;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;

public class TestFixture {

    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_EMAIL = "user@gmail.com";
    public static final short USER_RATING = 20;
    public static final Date PAYMENT_DATE = Date.valueOf("2022-12-02");
    public static final BigDecimal PAYMENT_PRICE = BigDecimal.valueOf(100.00);
    public static final short TRANSPORTATION_DISTANCE = 150;
    public static final String CARGO_NAME = "cargo_name";

    public static User getUser(Long id) {
        return User.builder()
                .id(id)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .rating(USER_RATING)
                .enabled(true)
                .roles(new HashSet<>())
                .build();
    }

    public static UserDto getUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .rating((int) USER_RATING)
                .enabled(true)
                .roles(new HashSet<>())
                .build();
    }

    public static Payment getPayment(Long id) {
        return Payment.builder()
                .id(id)
                .date(PAYMENT_DATE)
                .price(PAYMENT_PRICE)
                .build();
    }

    public static PaymentDto getPaymentDto(Long id) {
        return PaymentDto.builder()
                .id(id)
                .date(PAYMENT_DATE)
                .price(PAYMENT_PRICE)
                .build();
    }

    public static Transportation getTransportation(Long id) {
        return Transportation.builder()
                .id(id)
                .distance(TRANSPORTATION_DISTANCE)
                .user(getUser(1L))
                .payment(getPayment(id))
                .build();
    }

    public static SecureUserDto getSecureUserDto(Long id) {
        return SecureUserDto.builder()
                .id(id)
                .name(USER_NAME)
                .rating((int) USER_RATING)
                .email(USER_EMAIL)
                .build();
    }

    public static TransportationDto getTransportationDto(Long id) {
        return TransportationDto.builder()
                .id(id)
                .distance(TRANSPORTATION_DISTANCE)
                .user(getSecureUserDto(1L))
                .payment(getPaymentDto(id))
                .build();
    }

    public static Cargo getCargo(Long id) {
        return Cargo.builder()
                .id(id)
                .name(CARGO_NAME)
                .size(getSize(1L))
                .build();
    }

    public static CargoDto getCargoDto(Long id) {
        return CargoDto.builder()
                .id(id)
                .name(CARGO_NAME)
                .size(getSizeDto(1L))
                .build();
    }

    public static Address getAddress(Long id) {
        return Address.builder()
                .id(id)
                .street("Darvina")
                .build();
    }

    public static Size getSize(Long id) {
        return Size.builder()
                .id(id)
                .width(BigDecimal.valueOf(15.00))
                .build();
    }

    public static SizeDto getSizeDto(Long id) {
        return SizeDto.builder()
                .id(id)
                .width(BigDecimal.valueOf(15.00))
                .build();
    }
}
