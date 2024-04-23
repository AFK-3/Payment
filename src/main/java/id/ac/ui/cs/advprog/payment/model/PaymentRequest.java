package id.ac.ui.cs.advprog.payment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter
public class PaymentRequest {
    private UUID id;
    private String paymentStatus;
    private int paymentAmount;
    private Long paymentRequestTime;
    private Long paymentResponseTime;
    private UUID buyerID;
}