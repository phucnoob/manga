package uet.ppvan.mangareader.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uet.ppvan.mangareader.enums.Genre;


@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, String> {
    @Override
    public String convertToDatabaseColumn(Genre attribute) {
        return attribute.getValue();
    }

    @Override
    public Genre convertToEntityAttribute(String dbData) {
        return Genre.fromString(dbData);
    }
}
