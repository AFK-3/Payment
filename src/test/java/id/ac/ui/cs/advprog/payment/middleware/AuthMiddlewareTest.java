package id.ac.ui.cs.advprog.payment.middleware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthMiddlewareTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthMiddleware authMiddleware;

    private final String token = String.format("Bearer %s", fetchToken("aku", "pass"));

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public static String fetchToken(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://35.198.243.155/api/auth/login?username=" + username + "&password=" + password;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String token = response.getBody();
            System.out.println("Token: " + token);
            return token;
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
            return "SALAH";
        }
    }

    @Test
    public void testGetUsernameFromToken() {
        // Mock the response
        ResponseEntity<String> responseEntity = new ResponseEntity<>("aku", HttpStatus.OK);
        when(restTemplate.exchange(
                eq("http://35.198.243.155/user/get-username"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(responseEntity);

        // Call the method
        String username = AuthMiddleware.getUsernameFromToken(token);
        System.out.println(token);
        assertEquals("aku", username);
    }

    @Test
    public void testGetUsernameFromTokenWithException() {
        // Mock an exception
        when(restTemplate.exchange(
                eq("http://35.198.243.155/user/get-username"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RestClientException("Request failed"));

        // Call the method
        String username = AuthMiddleware.getUsernameFromToken("INVALID TOKEN");

        // Assert the result
        assertNull(username);
    }

    @Test
    public void testGetRoleFromToken() {
        // Mock the response
        ResponseEntity<String> responseEntity = new ResponseEntity<>("BUYER", HttpStatus.OK);
        when(restTemplate.exchange(
                eq("http://35.198.243.155/user/get-role"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(responseEntity);

        // Call the method
        String role = AuthMiddleware.getRoleFromToken(token);

        // Assert the result
        assertEquals("BUYER", role);
    }

    @Test
    public void testGetRoleFromTokenWithException() {
        // Mock an exception
        when(restTemplate.exchange(
                eq("http://35.198.243.155/user/get-role"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RestClientException("Request failed"));

        // Call the method
        String role = AuthMiddleware.getRoleFromToken("INVALID TOKEN");

        // Assert the result
        assertNull(role);
    }
}
