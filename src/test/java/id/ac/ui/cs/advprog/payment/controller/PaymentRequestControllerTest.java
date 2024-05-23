package id.ac.ui.cs.advprog.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import id.ac.ui.cs.advprog.payment.middleware.AuthMiddleware;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentRequestController.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaymentRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentRequestService paymentRequestService;

    @MockBean
    private PaymentRequestBuilder paymentRequestBuilder;

    @MockBean
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentRequestController paymentRequestController;

    private PaymentRequest paymentRequest;

    @BeforeEach
    public void setup() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setId("1");
        paymentRequest.setPaymentAmount(100);
        paymentRequest.setBuyerUsername("testBuyer");
        paymentRequest.setPaymentStatus(PaymentRequestStatus.WAITING_RESPONSE.getStatus());
    }
}
