package database.Hospital.repository;

import database.Hospital.model.Appointments;
import database.Hospital.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    List<Payments> findByPaymentStatus(String paymentStatus);

    List<Payments> findByAppointmentIsNull();
}


