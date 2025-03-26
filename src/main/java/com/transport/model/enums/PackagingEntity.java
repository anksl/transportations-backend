package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PackagingEntity {

    PALLET(1),
    BOX(2),
    BAG(3),
    TAPE(4);

    private final Integer id;

    public static PackagingEntity get(Integer id) {
        return Arrays.stream(PackagingEntity.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(PackagingEntity.class, id));
    }
}
