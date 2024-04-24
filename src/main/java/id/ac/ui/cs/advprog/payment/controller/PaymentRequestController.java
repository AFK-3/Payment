package id.ac.ui.cs.advprog.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        paymentRequest = paymentRequestService.create(paymentRequest);

        String paymentRequestJson = null;
        try {
            paymentRequestJson = objectMapper.writeValueAsString(paymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"paymentRequest\":" + paymentRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/get-all")
    public ResponseEntity<String> getAllPaymentRequest() {
        List<PaymentRequest> paymentRequestList = paymentRequestService.findAll();

        String paymentsRequestJson = null;
        try {
            paymentsRequestJson = objectMapper.writeValueAsString(paymentRequestList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"paymentsRequest\":" + paymentsRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/get-all-by-buyer-id/{buyerID}")
    public ResponseEntity<String> getAllPaymentRequestByBuyerId(@PathVariable UUID buyerID) {
        List<PaymentRequest> paymentRequestList = paymentRequestService.findAllByBuyerId(buyerID);

        String paymentsRequestJson = null;
        try {
            paymentsRequestJson = objectMapper.writeValueAsString(paymentRequestList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"paymentsRequest\":" + paymentsRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping("/get-one-by-id/{id}")
    public ResponseEntity<String> getPaymentRequestById(@PathVariable UUID id) {
        PaymentRequest paymentRequest = paymentRequestService.findById(id);

        String paymentRequestJson = null;
        try {
            paymentRequestJson = objectMapper.writeValueAsString(paymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"paymentsRequest\":" + paymentRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> deletePaymentRequestById(@PathVariable UUID id) {
        PaymentRequest deletedPaymentRequest = paymentRequestService.deletePaymentRequestById(id);

        String deletedPaymentRequestJson = null;
        try {
            deletedPaymentRequestJson = objectMapper.writeValueAsString(deletedPaymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"deletedPaymentsRequest\":" + deletedPaymentRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPaymentRequest(@PathVariable UUID id) {
        PaymentRequest cancelledPaymentRequest = paymentRequestService.findById(id);
        cancelledPaymentRequest.setPaymentStatus(PaymentRequestStatus.CANCELLED.getStatus());
        paymentRequestService.update(cancelledPaymentRequest);

        String cancelledPaymentRequestJson = null;
        try {
            cancelledPaymentRequestJson = objectMapper.writeValueAsString(cancelledPaymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"deletedPaymentsRequest\":" + cancelledPaymentRequestJson + "}";
        return ResponseEntity.ok(responseJson);
    }

}
