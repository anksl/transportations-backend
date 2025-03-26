package com.transport.model.enums;

import com.transport.api.exception.NoSuchEnumException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoadApproachEntity {

    FTL(1),
    LTL(2);

    private final Integer id;

    public static LoadApproachEntity get(Integer id) {
        return Arrays.stream(LoadApproachEntity.values())
            .filter(it -> Objects.equals(it.id, id))
            .findFirst()
            .orElseThrow(() -> NoSuchEnumException.create(LoadApproachEntity.class, id));
    }
}
