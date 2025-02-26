package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 让ID自动递增
    private Long patientid;

    @Column(name = "patientname", nullable = false) // 数据库中的字段是 patientname
    private String patientname;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob; // 出生日期

    @Transient
    private int age; // 计算年龄

    @Column(unique = true)
    private String email;

    @Column
    private String address;

    // **构造方法**
    public Patients() {}

    public Patients(Long patientid, String patientname, String gender, LocalDate dob, String email, String address) {
        this.patientid = patientid;
        this.patientname = patientname;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.age = calculateAge();
    }

    // **计算年龄**
    public int calculateAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    // **Getter & Setter**
    public Long getId() { return patientid; }
    public void setId(Long id) { this.patientid = id; }

    public String getPatientname() { return patientname; }  // **注意方法名拼写**
    public void setPatientname(String patientname) { this.patientname = patientname; } // **这个方法必须存在**

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) {
        this.dob = dob;
        this.age = calculateAge();
    }

    public int getAge() { return calculateAge(); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
