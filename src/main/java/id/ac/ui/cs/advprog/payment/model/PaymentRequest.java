package id.ac.ui.cs.advprog.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "payment")
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_payment")
    private String id;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_amount")
    private int paymentAmount;

    @Column(name = "payment_request_time")
    private Long paymentRequestTime;

    @Column(name = "payment_response_time")
    private Long paymentResponseTime;

    @Column(name = "buyer_username")
    private String buyerUsername;
}