package database.Hospital.repository;

import database.Hospital.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patients, Long> {
    Optional<Patients> findByEmail(String email);
}
