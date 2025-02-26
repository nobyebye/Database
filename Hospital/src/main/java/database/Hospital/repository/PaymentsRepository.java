package database.Hospital.repository;

import database.Hospital.model.Payments;
import database.Hospital.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    List<Payments> findByPaymentStatus(String paymentStatus);
    List<Payments> findByAppointment(Appointments appointment);
    Optional<Payments> findByAppointmentId(Long appointmentId);
}
