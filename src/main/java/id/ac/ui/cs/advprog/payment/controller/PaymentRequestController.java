package id.ac.ui.cs.advprog.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.middleware.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RestController
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
    public ResponseEntity<String> createPaymentRequest(@RequestBody PaymentRequest paymentRequest,
                                                       @RequestHeader("Authorization") String token) {
        String buyerUsername = AuthMiddleware.getUsernameFromToken(token);
        String buyerRole = AuthMiddleware.getRoleFromToken(token);
        if (buyerUsername == null || buyerRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        if (! buyerRole.equals("BUYER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role must be Buyer");
        }

        paymentRequest.setBuyerUsername(buyerUsername);
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
    public ResponseEntity<String> getAllPaymentRequest(Model model) {
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

    @GetMapping("/get-all-by-buyer-username/{buyerUsername}")
    public ResponseEntity<String> getAllPaymentRequestByBuyerUsername(@PathVariable String buyerUsername) {
        List<PaymentRequest> paymentRequestList = paymentRequestService.findAllByBuyerUsername(buyerUsername);

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
    public ResponseEntity<String> deletePaymentRequestById(@PathVariable UUID id,
                                                           @RequestHeader("Authorization") String token) {
        String buyerUsername = AuthMiddleware.getUsernameFromToken(token);
        String buyerRole = AuthMiddleware.getRoleFromToken(token);
        if (buyerUsername == null || buyerRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        if (! buyerRole.equals("BUYER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role must be Buyer");
        }

        PaymentRequest deletedPaymentRequest = paymentRequestService.deletePaymentRequestById(id);
        if (deletedPaymentRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID doesn't exist");
        }

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
    public ResponseEntity<String> cancelPaymentRequest(@PathVariable UUID id,
                                                       @RequestHeader("Authorization") String token) {
        String buyerUsername = AuthMiddleware.getUsernameFromToken(token);
        String buyerRole = AuthMiddleware.getRoleFromToken(token);
        if (buyerUsername == null || buyerRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        if (! buyerRole.equals("BUYER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role must be Buyer");
        }

        PaymentRequest cancelledPaymentRequest = paymentRequestService.findById(id);
        if (cancelledPaymentRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID doesn't exist");
        }
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

    @PatchMapping("/respond/{id}/{response}")
    public ResponseEntity<String> respondPaymentRequest(@PathVariable UUID id,
                                                       @PathVariable String response,
                                                       @RequestHeader("Authorization") String token) {
        String buyerUsername = AuthMiddleware.getUsernameFromToken(token);
        String buyerRole = AuthMiddleware.getRoleFromToken(token);
        if (buyerUsername == null || buyerRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        if (! buyerRole.equals("STAFF")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role must be Staff");
        }

        PaymentRequest respondedPaymentRequest = paymentRequestService.findById(id);
        if (respondedPaymentRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID doesn't exist");
        }
        if (! response.equals("ACCEPT") && ! response.equals("REJECT")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Response must be ACCEPT or REJECT");
        }

        if (response.equals("ACCEPT"))
            respondedPaymentRequest.setPaymentStatus(PaymentRequestStatus.ACCEPTED.getStatus());
        else
            respondedPaymentRequest.setPaymentStatus(PaymentRequestStatus.REJECTED.getStatus());

        paymentRequestService.update(respondedPaymentRequest);

        String respondedPaymentRequestJson = null;
        try {
            respondedPaymentRequestJson = objectMapper.writeValueAsString(respondedPaymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseJson = "{\"deletedPaymentsRequest\":" + respondedPaymentRequestJson + "}";

        return ResponseEntity.ok(responseJson);
    }

}
