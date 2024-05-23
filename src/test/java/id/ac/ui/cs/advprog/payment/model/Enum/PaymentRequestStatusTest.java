package id.ac.ui.cs.advprog.payment.model.Enum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaymentRequestStatusTest {

    @Test
    void testContains() {
        assertTrue(PaymentRequestStatus.contains("WAITING_RESPONSE"));
        assertTrue(PaymentRequestStatus.contains("ACCEPTED"));
        assertTrue(PaymentRequestStatus.contains("REJECTED"));
        assertTrue(PaymentRequestStatus.contains("CANCELLED"));
        assertFalse(PaymentRequestStatus.contains("UNKNOWN_STATUS"));
    }

    @Test
    void testGetStatus() {
        assertEquals("WAITING_RESPONSE", PaymentRequestStatus.WAITING_RESPONSE.getStatus());
        assertEquals("ACCEPTED", PaymentRequestStatus.ACCEPTED.getStatus());
        assertEquals("REJECTED", PaymentRequestStatus.REJECTED.getStatus());
        assertEquals("CANCELLED", PaymentRequestStatus.CANCELLED.getStatus());
    }
}
