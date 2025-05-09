package com.transport.converter;

import com.transport.model.enums.PaymentStatusEntity;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusConverter implements
    AttributeConverter<PaymentStatusEntity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentStatusEntity status) {
        return status.getId();
    }

    @Override
    public PaymentStatusEntity convertToEntityAttribute(Integer id) {
        return PaymentStatusEntity.get(id);
    }
}
