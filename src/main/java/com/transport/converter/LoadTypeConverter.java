package com.transport.converter;

import com.transport.model.enums.LoadMethodEntity;
import com.transport.model.enums.LoadTypeEntity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoadTypeConverter implements
    AttributeConverter<LoadTypeEntity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LoadTypeEntity type) {
        return type.getId();
    }

    @Override
    public LoadTypeEntity convertToEntityAttribute(Integer id) {
        return LoadTypeEntity.get(id);
    }
}
