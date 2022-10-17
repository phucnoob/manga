package uet.ppvan.mangareader.mangas.converter;

import uet.ppvan.mangareader.mangas.enums.Genre;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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