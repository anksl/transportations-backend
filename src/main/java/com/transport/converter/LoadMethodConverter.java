package com.transport.converter;

import com.transport.model.enums.LoadMethodEntity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoadMethodConverter implements
    AttributeConverter<LoadMethodEntity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LoadMethodEntity type) {
        return type.getId();
    }

    @Override
    public LoadMethodEntity convertToEntityAttribute(Integer id) {
        return LoadMethodEntity.get(id);
    }
}
