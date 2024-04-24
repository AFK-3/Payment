package id.ac.ui.cs.advprog.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-request")
public class PaymentRequestController {
    private PaymentRequestService paymentRequestService;
    private ObjectMapper objectMapper;

    @Autowired
    public PaymentRequestController(PaymentRequestService paymentRequestService, ObjectMapper objectMapper) {
        this.paymentRequestService = paymentRequestService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPaymentRequest(@RequestBody PaymentRequest paymentRequest) {
        try {
            String paymentRequestJson = objectMapper.writeValueAsString(paymentRequest);
            String responseJson = "{\"paymentRequest\":" + paymentRequestJson + "}";
            return ResponseEntity.ok(responseJson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing payment request");
        }
    }
}
