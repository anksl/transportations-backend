package com.transport.converter;

import com.transport.model.enums.LoadApproachEntity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoadApproachConverter implements
    AttributeConverter<LoadApproachEntity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LoadApproachEntity type) {
        return type.getId();
    }

    @Override
    public LoadApproachEntity convertToEntityAttribute(Integer id) {
        return LoadApproachEntity.get(id);
    }
}
