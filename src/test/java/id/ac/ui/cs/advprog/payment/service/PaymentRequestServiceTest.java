package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.repository.PaymentRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PaymentRequestServiceTest {

    @Mock
    private PaymentRequestBuilder paymentRequestBuilder;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @InjectMocks
    private PaymentRequestImpl paymentRequestService;

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setId("1");
        paymentRequest.setPaymentAmount(100);
        paymentRequest.setBuyerUsername("testUser");
    }

    @Test
    void testCreate() {
        when(paymentRequestBuilder.reset()).thenReturn(paymentRequestBuilder);
        when(paymentRequestBuilder.addPaymentAmount(anyInt())).thenReturn(paymentRequestBuilder);
        when(paymentRequestBuilder.addBuyerUsername(any(String.class))).thenReturn(paymentRequestBuilder);
        when(paymentRequestBuilder.build()).thenReturn(paymentRequest);
        when(paymentRequestRepository.save(any(PaymentRequest.class))).thenReturn(paymentRequest);

        PaymentRequest createdPaymentRequest = paymentRequestService.create(paymentRequest);

        assertNotNull(createdPaymentRequest);
        assertEquals("testUser", createdPaymentRequest.getBuyerUsername());
        verify(paymentRequestRepository, times(1)).save(paymentRequest);
    }

    @Test
    void testFindAll() {
        List<PaymentRequest> paymentRequests = Arrays.asList(paymentRequest);
        when(paymentRequestRepository.findAll()).thenReturn(paymentRequests);

        List<PaymentRequest> result = paymentRequestService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRequestRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(paymentRequestRepository.findById("1")).thenReturn(Optional.of(paymentRequest));

        PaymentRequest result = paymentRequestService.findById("1");

        assertNotNull(result);
        assertEquals("testUser", result.getBuyerUsername());
        verify(paymentRequestRepository, times(1)).findById("1");
    }

    @Test
    void testFindByUsername() {
        List<PaymentRequest> paymentRequests = Arrays.asList(paymentRequest);
        when(paymentRequestRepository.findByBuyerUsername("testUser")).thenReturn(paymentRequests);

        List<PaymentRequest> result = paymentRequestService.findByUsername("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRequestRepository, times(1)).findByBuyerUsername("testUser");
    }

    @Test
    void testUpdate() {
        when(paymentRequestRepository.findById("1")).thenReturn(Optional.of(paymentRequest));
        when(paymentRequestRepository.save(paymentRequest)).thenReturn(paymentRequest);

        PaymentRequest updatedPaymentRequest = paymentRequestService.update(paymentRequest);

        assertNotNull(updatedPaymentRequest);
        assertEquals("testUser", updatedPaymentRequest.getBuyerUsername());
        verify(paymentRequestRepository, times(1)).findById("1");
        verify(paymentRequestRepository, times(1)).save(paymentRequest);
    }

    @Test
    void testDeleteById() {
        when(paymentRequestRepository.findById("1")).thenReturn(Optional.of(paymentRequest));

        PaymentRequest deletedPaymentRequest = paymentRequestService.deleteById("1");

        assertNotNull(deletedPaymentRequest);
        verify(paymentRequestRepository, times(1)).findById("1");
        verify(paymentRequestRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteAll() throws ExecutionException, InterruptedException {
        List<PaymentRequest> paymentRequests = Arrays.asList(paymentRequest);
        when(paymentRequestRepository.findAll()).thenReturn(paymentRequests);

        CompletableFuture<List<PaymentRequest>> future = paymentRequestService.deleteAll();
        List<PaymentRequest> result = future.get();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRequestRepository, times(1)).findAll();
        verify(paymentRequestRepository, times(1)).deleteAll();
    }
}
