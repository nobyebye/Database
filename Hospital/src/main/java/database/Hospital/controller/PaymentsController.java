package database.Hospital.controller;

import database.Hospital.model.Appointments;
import database.Hospital.model.PaymentMethod;
import database.Hospital.model.Payments;
import database.Hospital.repository.AppointmentsRepository;
import database.Hospital.repository.PaymentsRepository;
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

    @GetMapping
    public String listPayments(Model model) {
        List<Payments> payments = paymentsRepository.findAll();
        model.addAttribute("payments", payments);
        return "payment-list";
    }

    @GetMapping("/new")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("payment", new Payments());

        List<Appointments> appointments = appointmentsRepository.findByPaymentIsNull();
        model.addAttribute("appointments", appointments);

        return "add-payment";
    }

    @PostMapping("/save")
    public String savePayment(@ModelAttribute Payments payment, Model model) {
        if (payment.getAppointment() == null) {
            model.addAttribute("error", "必须选择一个预约！");
            return "add-payment";
        }

        Optional<Appointments> appointmentOpt = appointmentsRepository.findById(payment.getAppointment().getAppointmentId());
        if (appointmentOpt.isEmpty() || appointmentOpt.get().getPayment() != null) {
            model.addAttribute("error", "该预约已存在支付记录！");
            return "add-payment";
        }

        payment.setPaymentDate(LocalDate.now());
        paymentsRepository.save(payment);
        return "redirect:/payments";
    }
}
