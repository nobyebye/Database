package database.Hospital.repository;

import database.Hospital.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors, Long> {


    @Query("SELECT d FROM Doctors d LEFT JOIN FETCH d.department")
    List<Doctors> findAllDoctorsWithAge();

    @Query("SELECT d FROM Doctors d WHERE d.department.departmentName = :departmentName")
    List<Doctors> findDoctorsByDepartment(@Param("departmentName") String departmentName);

    boolean existsByDoctorNameAndEmail(String doctorName, String email);
}
