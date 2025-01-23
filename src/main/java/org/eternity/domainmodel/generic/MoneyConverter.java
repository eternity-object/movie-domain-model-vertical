package org.eternity.domainmodel.generic;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money attribute) {
        return attribute.longValue();
    }

    @Override
    public Money convertToEntityAttribute(Long dbData) {
        return Money.wons(dbData);
    }
}
