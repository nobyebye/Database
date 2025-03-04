package database.Hospital.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentid")
    private Long departmentId;

    @Column(name = "departmentname", nullable = false, unique = true)
    private String departmentName;

    @Column(name = "location")
    private String location;

    public Departments() {}

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
