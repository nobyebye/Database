package database.Hospital.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatus status) {
        return (status != null) ? status.name().toLowerCase() : null;
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(String dbData) {
        return (dbData != null) ? AppointmentStatus.fromString(dbData) : null;
    }
}
