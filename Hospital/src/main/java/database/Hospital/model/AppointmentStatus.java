package database.Hospital.model;

public enum AppointmentStatus {
    INCOMPLETE, COMPLETED;

    public static AppointmentStatus fromString(String status) {
        for (AppointmentStatus s : AppointmentStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid AppointmentStatus: " + status);
    }
}
