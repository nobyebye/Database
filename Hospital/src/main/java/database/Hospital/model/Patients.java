package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;  // ✅ 添加这个导入

@Entity
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientid")
    private Long patientId;

    @Column(name = "patientname", nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;  // 出生日期

    @Column(unique = true)
    private String email;

    @Column
    private String address;

    @Transient
    private Integer age;

    public Patients() {}

    public Patients(String patientName, String gender, LocalDate dob, String email, String address) {
        this.patientName = patientName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.address = address;
    }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getAge() {
        return (dob != null) ? Period.between(dob, LocalDate.now()).getYears() : null;
    }
}
