package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN(1),
    TRANSPORTER(2),
    FORWARDER(3),
    CUSTOMER(4);

    private final Integer id;

    public static RoleType get(Integer id) {
        return Arrays.stream(RoleType.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(RoleType.class, id));
    }
}