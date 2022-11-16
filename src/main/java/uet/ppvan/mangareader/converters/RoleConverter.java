package uet.ppvan.mangareader.converters;

import uet.ppvan.mangareader.models.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
