package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "doctors")
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorid")
    private Long doctorId;

    @Column(name = "doctorname", nullable = false, length = 100)
    private String doctorName;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "departmentid", nullable = true)
    private Departments department;

    public Doctors() {}

    public Doctors(String doctorName, String gender, LocalDate dob, String specialization, String email, Departments department) {
        this.doctorName = doctorName;
        this.gender = gender;
        this.dob = dob;
        this.specialization = specialization;
        this.email = email;
        this.department = department;
    }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Departments getDepartment() { return department; }
    public void setDepartment(Departments department) { this.department = department; }
}
