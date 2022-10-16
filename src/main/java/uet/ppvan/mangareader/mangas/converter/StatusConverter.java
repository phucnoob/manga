package uet.ppvan.mangareader.mangas.converter;

import uet.ppvan.mangareader.mangas.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
