package database.Hospital.controller;

import database.Hospital.model.Payments;
import database.Hospital.model.Appointments;
import database.Hospital.repository.PaymentsRepository;
import database.Hospital.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    // 显示所有支付记录
    @GetMapping
    public String listPayments(Model model) {
        List<Payments> payments = paymentsRepository.findAll();
        model.addAttribute("payments", payments);
        return "payment-list"; // Thymeleaf 模板
    }

    // 显示添加支付记录页面
    @GetMapping("/new")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("payment", new Payments());
        model.addAttribute("appointments", appointmentsRepository.findAll());
        return "add-payment";
    }

    // 处理添加支付记录请求
    @PostMapping("/save")
    public String savePayment(
            @RequestParam("appointmentId") Long appointmentId,
            @RequestParam("amount") Double amount,
            @RequestParam("paymentStatus") String paymentStatus,
            @RequestParam("paymentMethod") Payments.PaymentMethod paymentMethod,
            Model model
    ) {
        Optional<Appointments> appointment = appointmentsRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            model.addAttribute("error", "预约 ID 无效！");
            return "add-payment";
        }

        Optional<Payments> existingPayment = paymentsRepository.findByAppointmentId(appointmentId);
        if (existingPayment.isPresent()) {
            model.addAttribute("error", "该预约已存在支付记录！");
            return "add-payment";
        }

        Payments payment = new Payments();
        payment.setAppointment(appointment.get());
        payment.setAmount(BigDecimal.valueOf(amount)); // **转换 Double → BigDecimal**
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDate.now()); // **设置当前日期**

        paymentsRepository.save(payment);
        model.addAttribute("success", "支付记录添加成功！");
        return "redirect:/payments";
    }

    // 删除支付记录
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, Model model) {
        if (!paymentsRepository.existsById(id)) {
            model.addAttribute("error", "支付记录不存在！");
            return "payment-list";
        }
        paymentsRepository.deleteById(id);
        model.addAttribute("success", "支付记录删除成功！");
        return "redirect:/payments";
    }
}
