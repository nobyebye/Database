package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medicalrecords")  // 表名修正
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自动递增
    private Long recordid;

    @ManyToOne  // 修改为 ManyToOne，一个预约可能有多个病历
    @JoinColumn(name = "appointmentid", nullable = false)
    private Appointments appointment; // 关联预约信息

    @ManyToOne  // 关联病人
    @JoinColumn(name = "patientid", nullable = false)
    private Patients patient;

    @ManyToOne  // 关联医生
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctors doctor;

    @Column(nullable = false)
    private String diagnosis; // 诊断

    @Column(name = "treatmentplan", nullable = false)
    private String treatmentPlan; // 治疗方案

    @Column
    private String prescription; // 处方

    @Column(name = "testresults")
    private String testResults; // 检查结果

    @Column(name = "visitdate", nullable = false)
    private LocalDate visitDate = LocalDate.now(); // 默认当前日期

    // 构造方法
    public MedicalRecords() {}

    public MedicalRecords(Appointments appointment, Patients patient, Doctors doctor, String diagnosis, String treatmentPlan, String prescription, String testResults, LocalDate visitDate) {
        this.appointment = appointment;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
        this.testResults = testResults;
        this.visitDate = visitDate;
    }

    // Getter & Setter
    public Long getId() { return recordid; }
    public void setId(Long id) { this.recordid = id; }

    public Appointments getAppointment() { return appointment; }
    public void setAppointment(Appointments appointment) { this.appointment = appointment; }

    public Patients getPatient() { return patient; }
    public void setPatient(Patients patient) { this.patient = patient; }

    public Doctors getDoctor() { return doctor; }
    public void setDoctor(Doctors doctor) { this.doctor = doctor; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getTestResults() { return testResults; }
    public void setTestResults(String testResults) { this.testResults = testResults; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
}
