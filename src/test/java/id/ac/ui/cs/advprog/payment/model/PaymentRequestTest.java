package id.ac.ui.cs.advprog.payment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentRequestTest {

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
    }

    @Test
    void testGetAndSetId() {
        String id = UUID.randomUUID().toString();
        paymentRequest.setId(id);
        assertEquals(id, paymentRequest.getId());
    }

    @Test
    void testGetAndSetPaymentStatus() {
        String status = "ACCEPTED";
        paymentRequest.setPaymentStatus(status);
        assertEquals(status, paymentRequest.getPaymentStatus());
    }

    @Test
    void testGetAndSetPaymentAmount() {
        int amount = 100;
        paymentRequest.setPaymentAmount(amount);
        assertEquals(amount, paymentRequest.getPaymentAmount());
    }

    @Test
    void testGetAndSetPaymentRequestTime() {
        Long requestTime = System.currentTimeMillis();
        paymentRequest.setPaymentRequestTime(requestTime);
        assertEquals(requestTime, paymentRequest.getPaymentRequestTime());
    }

    @Test
    void testGetAndSetPaymentResponseTime() {
        Long responseTime = System.currentTimeMillis();
        paymentRequest.setPaymentResponseTime(responseTime);
        assertEquals(responseTime, paymentRequest.getPaymentResponseTime());
    }

    @Test
    void testGetAndSetBuyerUsername() {
        String username = "testUser";
        paymentRequest.setBuyerUsername(username);
        assertEquals(username, paymentRequest.getBuyerUsername());
    }

    @Test
    void testNoArgsConstructor() {
        PaymentRequest paymentRequest = new PaymentRequest();
        assertNotNull(paymentRequest);
    }

    @Test
    void testAllArgsConstructor() {
        String id = UUID.randomUUID().toString();
        String status = "ACCEPTED";
        int amount = 100;
        Long requestTime = System.currentTimeMillis();
        Long responseTime = System.currentTimeMillis();
        String username = "testUser";

        PaymentRequest paymentRequest = new PaymentRequest(id, status, amount, requestTime, responseTime, username);

        assertEquals(id, paymentRequest.getId());
        assertEquals(status, paymentRequest.getPaymentStatus());
        assertEquals(amount, paymentRequest.getPaymentAmount());
        assertEquals(requestTime, paymentRequest.getPaymentRequestTime());
        assertEquals(responseTime, paymentRequest.getPaymentResponseTime());
        assertEquals(username, paymentRequest.getBuyerUsername());
    }
}
