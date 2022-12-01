package uet.ppvan.mangareader.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uet.ppvan.mangareader.enums.Status;


@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.value();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Status.fromString(dbData);
    }
}
