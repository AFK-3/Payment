package id.ac.ui.cs.advprog.payment.middleware;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthMiddleware {
    public static String getUsernameFromToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> owner = restTemplate.exchange("http://35.198.243.155/user/get-username", HttpMethod.GET, entity, String.class);
        return owner.getBody();
    }

    public static String getRoleFromToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> owner = restTemplate.exchange("http://35.198.243.155/user/get-role", HttpMethod.GET, entity, String.class);
        return owner.getBody();
    }

}