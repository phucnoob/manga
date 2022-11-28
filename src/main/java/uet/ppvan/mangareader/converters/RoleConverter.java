package uet.ppvan.mangareader.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uet.ppvan.mangareader.models.Role;


@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        return attribute.toString();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.valueOf(dbData);
    }
}
