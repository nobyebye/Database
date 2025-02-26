package database.Hospital.repository;

import database.Hospital.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {
    Optional<MedicalRecords> findByAppointmentId(Long appointmentId);
}
