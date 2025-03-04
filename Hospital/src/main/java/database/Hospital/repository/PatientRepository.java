package database.Hospital.repository;

import database.Hospital.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patients, Long> {
    Patients findByEmail(String email);

}
