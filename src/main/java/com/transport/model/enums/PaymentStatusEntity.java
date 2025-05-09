package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatusEntity {

    ON_TIME(1, (short) 20),
    LATE(2, (short) -10),
    OWE(3, (short) -20);

    private final Integer id;
    private static final Map<Short, PaymentStatus> LOOKUP;
    private final short val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(PaymentStatus.values())
            .collect(Collectors.toMap(PaymentStatus::getVal, Function.identity())));
    }

    public static PaymentStatusEntity get(Integer id) {
        return Arrays.stream(PaymentStatusEntity.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(PaymentStatusEntity.class, id));
    }
}
