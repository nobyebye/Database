package database.Hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors") // 确保与数据库表名匹配
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自动递增
    @Column(name = "doctorid")  // 确保和数据库字段一致
    private Long doctorId;

    @Column(name = "doctorname", nullable = false, length = 100)
    private String doctorName;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "departmentid", nullable = true) // 关联 departmentid
    private Departments department;

    // 无参构造方法（JPA 需要）
    public Doctors() {}

    // 带参构造方法
    public Doctors(String doctorName, String gender, int age, String specialization, String email, Departments department) {
        this.doctorName = doctorName;
        this.gender = gender;
        this.age = age;
        this.specialization = specialization;
        this.email = email;
        this.department = department;
    }

    // Getter & Setter 方法
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Departments getDepartment() { return department; }
    public void setDepartment(Departments department) { this.department = department; }
}
