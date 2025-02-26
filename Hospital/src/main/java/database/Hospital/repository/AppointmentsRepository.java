package database.Hospital.repository;

import database.Hospital.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {
    List<Appointments> findByBookingDate(LocalDate bookingDate);  // **方法名改为 findByBookingDate**
}
