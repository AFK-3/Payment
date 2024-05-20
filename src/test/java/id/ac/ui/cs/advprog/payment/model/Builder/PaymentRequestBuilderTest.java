package id.ac.ui.cs.advprog.payment.model.Builder;

import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRequestBuilderTest {

    private PaymentRequestBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new PaymentRequestBuilder();
    }

    @Test
    void testReset() {
        PaymentRequest paymentRequest = builder.reset().build();
        assertNotNull(paymentRequest.getId());
        assertNotNull(paymentRequest.getPaymentRequestTime());
        assertEquals(PaymentRequestStatus.WAITING_RESPONSE.getStatus(), paymentRequest.getPaymentStatus());
    }

    @Test
    void testFirstSetUp() {
        builder.reset();
        PaymentRequest paymentRequest = builder.firstSetUp().build();
        assertNotNull(paymentRequest.getId());
        assertNotNull(paymentRequest.getPaymentRequestTime());
        assertEquals(PaymentRequestStatus.WAITING_RESPONSE.getStatus(), paymentRequest.getPaymentStatus());
    }

    @Test
    void testAddPaymentStatusValid() {
        builder.reset();
        PaymentRequest paymentRequest = builder.addPaymentStatus("ACCEPTED").build();
        assertEquals("ACCEPTED", paymentRequest.getPaymentStatus());
    }

    @Test
    void testAddPaymentStatusInvalid() {
        builder.reset();
        assertThrows(IllegalArgumentException.class, () -> builder.addPaymentStatus("INVALID_STATUS"));
    }

    @Test
    void testAddPaymentAmount() {
        builder.reset();
        PaymentRequest paymentRequest = builder.addPaymentAmount(100).build();
        assertEquals(100, paymentRequest.getPaymentAmount());
    }

    @Test
    void testAddPaymentResponseTime() {
        builder.reset();
        long responseTime = System.currentTimeMillis();
        PaymentRequest paymentRequest = builder.addPaymentResponseTime(responseTime).build();
        assertEquals(responseTime, paymentRequest.getPaymentResponseTime());
    }

    @Test
    void testSetPaymentResponseCurrentTime() {
        PaymentRequest paymentRequest = new PaymentRequest();
        PaymentRequestBuilder.setPaymentResponseCurrentTime(paymentRequest);
        assertNotNull(paymentRequest.getPaymentResponseTime());
    }

    @Test
    void testAddPaymentRequestTime() {
        builder.reset();
        long requestTime = System.currentTimeMillis();
        PaymentRequest paymentRequest = builder.addPaymentRequestTime(requestTime).build();
        assertEquals(requestTime, paymentRequest.getPaymentRequestTime());
    }

    @Test
    void testAddBuyerUsername() {
        builder.reset();
        PaymentRequest paymentRequest = builder.addBuyerUsername("testUser").build();
        assertEquals("testUser", paymentRequest.getBuyerUsername());
    }

    @Test
    void testBuild() {
        PaymentRequest paymentRequest = builder
                .reset()
                .addPaymentAmount(200)
                .addBuyerUsername("testUser")
                .build();
        assertNotNull(paymentRequest);
        assertEquals(200, paymentRequest.getPaymentAmount());
        assertEquals("testUser", paymentRequest.getBuyerUsername());
        assertEquals(PaymentRequestStatus.WAITING_RESPONSE.getStatus(), paymentRequest.getPaymentStatus());
    }

    @Test
    void testUpdateFields() {
        PaymentRequest existing = new PaymentRequest();
        PaymentRequest newData = new PaymentRequest();
        newData.setPaymentStatus("ACCEPTED");
        newData.setPaymentAmount(300);
        newData.setPaymentRequestTime(System.currentTimeMillis());
        newData.setPaymentResponseTime(System.currentTimeMillis());
        newData.setBuyerUsername("updatedUser");

        PaymentRequestBuilder.updateFields(existing, newData);

        assertEquals("ACCEPTED", existing.getPaymentStatus());
        assertEquals(300, existing.getPaymentAmount());
        assertEquals(newData.getPaymentRequestTime(), existing.getPaymentRequestTime());
        assertEquals(newData.getPaymentResponseTime(), existing.getPaymentResponseTime());
        assertEquals("updatedUser", existing.getBuyerUsername());
    }
}
