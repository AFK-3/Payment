package id.ac.ui.cs.advprog.payment.model.Builder;

import id.ac.ui.cs.advprog.payment.model.Enum.PaymentRequestStatus;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;

import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;

import java.util.UUID;

@Component
public class PaymentRequestBuilder {
    private PaymentRequest currentPaymentRequest;

    public PaymentRequestBuilder(){
        this.reset();
    }

    public PaymentRequestBuilder reset() {
        currentPaymentRequest = new PaymentRequest();
        firstSetUp();
        return this;
    }

    public PaymentRequestBuilder firstSetUp() {
        currentPaymentRequest.setId(UUID.randomUUID().toString());
        LocalTime now = LocalTime.now();
        Time orderTime = Time.valueOf(now);
        long orderTimeInLong = orderTime.getTime();
        this.addPaymentStatus(PaymentRequestStatus.WAITING_RESPONSE.getStatus());
        this.addPaymentRequestTime(orderTimeInLong);
        return this;
    }

    public PaymentRequestBuilder addPaymentStatus(String status) {
        if (PaymentRequestStatus.contains(status)) {
            currentPaymentRequest.setPaymentStatus(status);
            return this;
        }
        throw new IllegalArgumentException();
    }

    public PaymentRequestBuilder addPaymentAmount(int paymentAmount) {
        currentPaymentRequest.setPaymentAmount(paymentAmount);
        return this;
    }

    public PaymentRequestBuilder addPaymentResponseTime(long paymentResponseTime) {
        currentPaymentRequest.setPaymentResponseTime(paymentResponseTime);
        return this;
    }

    public static void setPaymentResponseCurrentTime(PaymentRequest paymentRequest) {
        LocalTime now = LocalTime.now();
        Time orderTime = Time.valueOf(now);
        long orderTimeInLong = orderTime.getTime();
        paymentRequest.setPaymentResponseTime(orderTimeInLong);
    }

    public PaymentRequestBuilder addPaymentRequestTime(long paymentRequestTime) {
        currentPaymentRequest.setPaymentRequestTime(paymentRequestTime);
        return this;
    }

    public PaymentRequestBuilder addBuyerUsername(String buyerUsername) {
        currentPaymentRequest.setBuyerUsername(buyerUsername);
        return this;
    }

    public PaymentRequest build() {
        return currentPaymentRequest;
    }

    public static void updateFields(PaymentRequest existing, PaymentRequest newData) {
        existing.setPaymentStatus(newData.getPaymentStatus());
        existing.setPaymentAmount(newData.getPaymentAmount());
        existing.setPaymentRequestTime(newData.getPaymentRequestTime());
        existing.setPaymentResponseTime(newData.getPaymentResponseTime());
        existing.setBuyerUsername(newData.getBuyerUsername());
    }
}