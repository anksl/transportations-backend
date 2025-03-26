package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadTypeEntity {

    MANUAL(1),
    AUTOMATED(2);

    private final Integer id;

    public static LoadTypeEntity get(Integer id) {
        return Arrays.stream(LoadTypeEntity.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(LoadTypeEntity.class, id));
    }
}
