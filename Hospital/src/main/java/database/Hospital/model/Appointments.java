package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentid;  // 预约 ID

    @Column(name = "bookingdate", nullable = false)  // **改为 "bookingdate"，与数据库表一致**
    private LocalDate bookingDate; // 预约日期

    @Column(name = "time", nullable = false)
    private LocalTime time;  // 预约时间

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctors doctor;

    @ManyToOne
    @JoinColumn(name = "patientid", nullable = false)
    private Patients patient;

    @Column(name = "status", nullable = false)
    private String status = "Scheduled"; // 预约状态（默认值）

    @Column(name = "paymentstatus", nullable = false)
    private String paymentStatus = "Pending"; // 默认支付状态

    // **构造方法**
    public Appointments() {}

    public Appointments(Doctors doctor, Patients patient, LocalDate bookingDate, LocalTime time, String status, String paymentStatus) {
        this.doctor = doctor;
        this.patient = patient;
        this.bookingDate = bookingDate;
        this.time = time;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    // **Getter & Setter**
    public Long getId() { return appointmentid; }
    public void setId(Long id) { this.appointmentid = id; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public Doctors getDoctor() { return doctor; }
    public void setDoctor(Doctors doctor) { this.doctor = doctor; }

    public Patients getPatient() { return patient; }
    public void setPatient(Patients patient) { this.patient = patient; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
