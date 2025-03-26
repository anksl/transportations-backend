package com.transport.converter;

import com.transport.model.enums.PackagingEntity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PackagingConverter implements
    AttributeConverter<PackagingEntity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PackagingEntity type) {
        return type.getId();
    }

    @Override
    public PackagingEntity convertToEntityAttribute(Integer id) {
        return PackagingEntity.get(id);
    }
}
