package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"patientid", "doctorid", "bookingdate"})
},
        indexes = {
        @Index(name="idx_appointments_doctorid", columnList = "doctorid")
        }

)
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentid")
    private Long id;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Payments payment;

    @ManyToOne
    @JoinColumn(name = "doctorid", referencedColumnName = "doctorid")
    private Doctors doctor;

    @ManyToOne
    @JoinColumn(name = "patientid", referencedColumnName = "patientid")
    private Patients patient;

    @Column(name = "bookingdate", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Convert(converter = AppointmentStatusConverter.class)
    private AppointmentStatus status;

    public Appointments() {}

    public Appointments(Doctors doctor, Patients patient, LocalDate bookingDate, LocalTime time, AppointmentStatus status) {
        this.doctor = doctor;
        this.patient = patient;
        this.bookingDate = bookingDate;
        this.time = time;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctors getDoctor() { return doctor; }
    public void setDoctor(Doctors doctor) { this.doctor = doctor; }

    public Patients getPatient() { return patient; }
    public void setPatient(Patients patient) { this.patient = patient; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public Long getAppointmentId() { return id; }
    public void setAppointmentId(Long appointmentId) { this.id = appointmentId; }

    public Payments getPayment() { return payment; }
    public void setPayment(Payments payment) { this.payment = payment; }
}
