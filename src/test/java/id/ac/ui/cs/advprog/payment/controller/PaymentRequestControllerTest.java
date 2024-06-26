package id.ac.ui.cs.advprog.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.payment.middleware.AuthMiddleware;
import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PaymentRequestController.class)
@ContextConfiguration(classes = {PaymentRequestControllerTest.TestConfig.class, PaymentRequestController.class})
@ActiveProfiles("test")
public class PaymentRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentRequestService paymentRequestService;

    @MockBean
    private AuthMiddleware authMiddleware;

    @MockBean
    private PaymentRequestBuilder paymentRequestBuilder;

    private PaymentRequest paymentRequest;

    private String STAFF_TOKEN;
    private String BUYER_TOKEN;

    @BeforeEach
    public void setup() throws Exception {
        paymentRequest = new PaymentRequest();
        paymentRequest.setId("7ac424ea-c319-4793-9346-c89b40dd2984");
        paymentRequest.setPaymentStatus("WAITING_RESPONSE");
        paymentRequest.setPaymentAmount(61);
        paymentRequest.setPaymentRequestTime(27325000L); // Corrected long literal
        paymentRequest.setPaymentResponseTime(null);
        paymentRequest.setBuyerUsername("aku");

        RestTemplate restTemplate = new RestTemplate();
        String loginUrl = "http://35.198.243.155/api/auth/login?username=akuStaff&password=pass";
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, null, String.class);
        STAFF_TOKEN = "Bearer " + response.getBody();

        restTemplate = new RestTemplate();
        loginUrl = "http://35.198.243.155/api/auth/login?username=aku&password=pass";
        response = restTemplate.postForEntity(loginUrl, null, String.class);
        BUYER_TOKEN = "Bearer " + response.getBody();

        when(paymentRequestService.deleteAll()).thenReturn(CompletableFuture.completedFuture(Collections.singletonList(paymentRequest)));
    }

    @Test
    public void testCreatePaymentRequest() throws Exception {
        when(paymentRequestService.create(any(PaymentRequest.class))).thenReturn(paymentRequest);

        mockMvc.perform(post("/payment-request/create")
                        .header("Authorization", BUYER_TOKEN+"aa11122a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentAmount\":100}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/payment-request/create")
                        .header("Authorization", STAFF_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentAmount\":100}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/payment-request/create")
                        .header("Authorization", BUYER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentAmount\":100}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"paymentRequest\":{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}}"));
    }

    @Test
    public void testGetAllPaymentRequests() throws Exception {
        List<PaymentRequest> paymentRequests = Collections.singletonList(paymentRequest);
        when(paymentRequestService.findAll()).thenReturn(paymentRequests);

        mockMvc.perform(get("/payment-request/get-all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"paymentsRequest\":[{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}]}"));
    }

    @Test
    public void testGetAllPaymentRequestsByBuyerUsername() throws Exception {
        List<PaymentRequest> paymentRequests = Collections.singletonList(paymentRequest);
        when(paymentRequestService.findByUsername("aku")).thenReturn(paymentRequests);
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJha3UiLCJpYXQiOjE3MTY0NDc2NTIsImV4cCI6MTcxNjQ1MTI1Mn0.ITlXCYR0blT-CAfpt5Jv7Nwr0mrchixeVvyY4QS9yyn9WWUKuWlgGQRDsww2YLyb81syblf5o02-Z9_Uu-SWdQ";

        mockMvc.perform(get("/payment-request/get-all-by-buyer-username")
                .header("Authorization", BUYER_TOKEN+"AAAxxx"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/payment-request/get-all-by-buyer-username")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/payment-request/get-all-by-buyer-username")
                .header("Authorization", BUYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"paymentsRequest\":[{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}]}"));
    }

    @Test
    public void testGetPaymentRequestById() throws Exception {
        when(paymentRequestService.findById("7ac424ea-c319-4793-9346-c89b40dd2984")).thenReturn(paymentRequest);

        mockMvc.perform(get("/payment-request/get-one-by-id/7ac424ea-c319-4793-9346-c89b40dd2984"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"paymentsRequest\":{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}}"));
    }

    @Test
    public void testDeletePaymentRequestById() throws Exception {
        when(paymentRequestService.deleteById("7ac424ea-c319-4793-9346-c89b40dd2984")).thenReturn(paymentRequest);

        mockMvc.perform(delete("/payment-request/delete-by-id/7ac424ea-c319-4793-9346-c89b40dd2984")
                        .header("Authorization", BUYER_TOKEN+"s"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/payment-request/delete-by-id/7ac424ea-c319-4793-9346-c89b40dd2984")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/payment-request/delete-by-id/7ac424ea-c319-4793-9346-c89b40dd2984")
                        .header("Authorization", BUYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"deletedPaymentsRequest\":{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}}"));

        mockMvc.perform(delete("/payment-request/delete-by-id/7ac424ea-c319-4793-9346-c89b40d")
                        .header("Authorization", BUYER_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteAllPaymentRequests() throws Exception {
        mockMvc.perform(delete("/payment-request/delete-all")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelPaymentRequest() throws Exception {
        when(paymentRequestService.findById("7ac424ea-c319-4793-9346-c89b40dd2984")).thenReturn(paymentRequest);

        mockMvc.perform(put("/payment-request/cancel/7ac424ea-c319-4793-9346-c89b40dd2984")
                        .header("Authorization", BUYER_TOKEN+"ss"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/payment-request/cancel/7ac424ea-c319-4793-9346-c89b40dd2984")
                .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isForbidden());

        mockMvc.perform(put("/payment-request/cancel/7ac424ea-c319-4793-9346-c89b40dd2984")
                        .header("Authorization", BUYER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"canceledPaymentsRequest\":{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"CANCELLED\",\"paymentAmount\":61,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}}"));

        mockMvc.perform(put("/payment-request/cancel/7ac424ea-c319-4793-9346-c89b40d")
                        .header("Authorization", BUYER_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRespondPaymentRequest() throws Exception {
        when(paymentRequestService.findById("7ac424ea-c319-4793-9346-c89b40dd2984")).thenReturn(paymentRequest);

        mockMvc.perform(put("/payment-request/respond/7ac424ea-c319-4793-9346-c89b40dd2984/ACCEPT")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isOk());

        mockMvc.perform(put("/payment-request/respond/7ac424ea-c319-4793-9346-c89b40dd2984/REJECT")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isOk());

        mockMvc.perform(put("/payment-request/respond/7ac424ea-c319-4793-9346-c89b40dd2984/ACCEPT2")
                        .header("Authorization", STAFF_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditPaymentRequest() throws Exception {
        int newAmount = 100;

        when(paymentRequestService.findById("7ac424ea-c319-4793-9346-c89b40dd2984")).thenReturn(paymentRequest);
        paymentRequest.setPaymentAmount(newAmount);
        when(paymentRequestService.update(any(PaymentRequest.class))).thenReturn(paymentRequest);

        String expectedJson = String.format("{\"editedPaymentsRequest\":{\"id\":\"7ac424ea-c319-4793-9346-c89b40dd2984\",\"paymentStatus\":\"WAITING_RESPONSE\",\"paymentAmount\":%d,\"paymentRequestTime\":27325000,\"paymentResponseTime\":null,\"buyerUsername\":\"aku\"}}", newAmount);

        mockMvc.perform(put("/payment-request/edit/7ac424ea-c319-4793-9346-c89b40dd2984/" + newAmount)
                        .header("Authorization", BUYER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        mockMvc.perform(put("/payment-request/edit/7ac424ea-c319-4793-9346-c89b40/" + newAmount)
                        .header("Authorization", BUYER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
