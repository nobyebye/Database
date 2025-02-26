package database.Hospital.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments") // 确保表名一致
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自动递增
    private Long paymentid;

    @OneToOne
    @JoinColumn(name = "appointmentid", nullable = false, unique = true)
    private Appointments appointment; // 关联预约信息

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount; // **改为 BigDecimal，避免精度问题**

    @Column(name = "paymentdate", nullable = false)
    private LocalDate paymentDate = LocalDate.now(); // **改为 LocalDate**

    @Column(name = "paymentstatus", nullable = false)
    private String paymentStatus = "Pending"; // 默认值

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentmethod", nullable = false)
    private PaymentMethod paymentMethod; // 支付方式

    // 枚举类
    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, CASH, ONLINE_TRANSFER
    }

    // 构造方法
    public Payments() {}

    public Payments(Appointments appointment, BigDecimal amount, String paymentStatus, PaymentMethod paymentMethod) {
        this.appointment = appointment;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    // Getter & Setter
    public Long getId() { return paymentid; }
    public void setId(Long id) { this.paymentid = id; }

    public Appointments getAppointment() { return appointment; }
    public void setAppointment(Appointments appointment) { this.appointment = appointment; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
}
