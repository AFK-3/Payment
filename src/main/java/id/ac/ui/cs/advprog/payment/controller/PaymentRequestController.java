package id.ac.ui.cs.advprog.payment.controller;

import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-request")
public class PaymentRequestController {
    private PaymentRequestService paymentRequestService;

    @PostMapping("/create")
    public String createPaymentRequest(@ModelAttribute("form")PaymentRequest paymentRequest) {
        System.out.println(paymentRequest);
        return "";
    }
}
