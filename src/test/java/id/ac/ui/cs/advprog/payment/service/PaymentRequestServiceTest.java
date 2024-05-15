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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PaymentRequestServiceTest {
    @InjectMocks
    PaymentRequestImpl paymentRequestImpl;

    @Mock
    PaymentRequestRepository paymentRequestRepository;

    List<PaymentRequest> paymentRequests = new ArrayList<>();

    @Mock
    private PaymentRequestBuilder paymentRequestBuilder;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        PaymentRequest paymentRequest1 = paymentRequestBuilder.reset()
                .addPaymentAmount(10)
                .addBuyerUsername("aku")
                .build();

        PaymentRequest paymentRequest2 = paymentRequestBuilder.reset()
                .addPaymentAmount(96)
                .addBuyerUsername("adi")
                .build();

        PaymentRequest paymentRequest3 = paymentRequestBuilder.reset()
                .addPaymentAmount(34)
                .addBuyerUsername("aku")
                .build();

        paymentRequests.add(paymentRequest1);
        paymentRequests.add(paymentRequest2);
        paymentRequests.add(paymentRequest3);
    }
}
