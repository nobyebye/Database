package database.Hospital.repository;

import database.Hospital.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors, Long> { // 修改主键类型为 Long
}
