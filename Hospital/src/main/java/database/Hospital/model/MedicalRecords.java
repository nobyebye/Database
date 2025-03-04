package database.Hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medicalrecords")
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordid;

    @OneToOne
    @JoinColumn(name = "appointmentid", nullable = false, unique = true)
    private Appointments appointment;

    @ManyToOne
    @JoinColumn(name = "patientid", nullable = false)
    private Patients patient;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false)
    private Doctors doctor;

    @Column(nullable = false)
    private String diagnosis;

    @Column(name = "treatmentplan", nullable = false)
    private String treatmentPlan;

    @Column
    private String prescription;

    @Column(name = "testresults")
    private String testResults;

    @Column(name = "visitdate", nullable = false)
    private LocalDate visitDate = LocalDate.now();

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
