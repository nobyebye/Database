package database.Hospital.repository;

import database.Hospital.model.Appointments;
import database.Hospital.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {

    List<Appointments> findByStatus(AppointmentStatus status);
    List<Appointments> findByPaymentIsNull();
    List<Appointments> findByDoctorDoctorId(Long doctorId);




}

