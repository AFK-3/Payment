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
        currentPaymentRequest.setId(UUID.randomUUID());
        LocalTime now = LocalTime.now();
        Time orderTime = Time.valueOf(now);
        long orderTimeInLong = orderTime.getTime();
        currentPaymentRequest.setPaymentStatus(PaymentRequestStatus.WAITING_RESPONSE.getStatus());
        currentPaymentRequest.setPaymentRequestTime(orderTimeInLong);
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

    public PaymentRequestBuilder addBuyerUsername(String buyerUsername) {
        currentPaymentRequest.setBuyerUsername(buyerUsername);
        return this;
    }

    public PaymentRequest build() {
        return currentPaymentRequest;
    }

    public PaymentRequestBuilder setCurrent(PaymentRequest paymentRequest) {
        currentPaymentRequest = paymentRequest;
        return this;
    }
}