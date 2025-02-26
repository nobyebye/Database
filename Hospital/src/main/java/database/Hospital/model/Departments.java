package database.Hospital.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments") // 确保表名匹配
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自动递增
    @Column(name = "departmentid") // 确保字段名匹配数据库
    private Long departmentId;

    @Column(name = "departmentname", nullable = false, unique = true, length = 100)
    private String departmentName;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctors> doctors; // 一个科室有多个医生

    // 无参构造方法（JPA 需要）
    public Departments() {}

    // 带参构造方法
    public Departments(String departmentName, String location) {
        this.departmentName = departmentName;
        this.location = location;
    }

    // Getter & Setter
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Doctors> getDoctors() { return doctors; }
    public void setDoctors(List<Doctors> doctors) { this.doctors = doctors; }
}
