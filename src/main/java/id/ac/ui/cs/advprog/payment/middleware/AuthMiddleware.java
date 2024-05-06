package id.ac.ui.cs.advprog.payment.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class AuthMiddleware {

    public static String getUsernameFromToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://35.198.243.155/user/get-username",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            System.err.println("Request failed: " + e.getMessage());
            return null;  // Return null if there's an exception
        }
    }


    public static String getRoleFromToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://35.198.243.155/user/get-role",
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            System.err.println("Request failed: " + e.getMessage());
            return null;
        }
    }


}