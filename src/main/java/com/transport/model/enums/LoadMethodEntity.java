package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadMethodEntity {
    ABOVE(1),
    SIDE(2),
    BEHIND(3);

    private final Integer id;

    public static LoadMethodEntity get(Integer id) {
        return Arrays.stream(LoadMethodEntity.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(LoadMethodEntity.class, id));
    }
}