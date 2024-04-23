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

    public PaymentRequestBuilder addBuyerID(UUID buyerId) {
        currentPaymentRequest.setBuyerID(buyerId);
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

//public class OrderBuilder {
//    private Order currentOrder;
//
//    public OrderBuilder(){
//        this.reset();
//    }
//
//    public OrderBuilder reset(){
//        currentOrder = new Order();
//        firstSetUp();
//        return this;
//    }
//
//    public OrderBuilder addAuthor(String username){
//        currentOrder.setAuthorUsername(username);
//        return this;
//    }
//
//    public OrderBuilder addStatus(String status){
//        if (OrderStatus.contains(status)){
//            currentOrder.setStatus(status);
//            return this;
//        }else{
//            throw new IllegalArgumentException();
//        }
//    }
//
//    public OrderBuilder addListings(List<Listing> listings){
//        if (!listings.isEmpty()){
//            currentOrder.setListings(listings);
//            return this;
//        }else{
//            throw new IllegalArgumentException();
//        }
//    }
//
//    public OrderBuilder setCurrent(Order newOrder){
//        currentOrder = newOrder;
//        return this;
//    }
//
//    public OrderBuilder firstSetUp(){
//        currentOrder.setId(UUID.randomUUID());
//        LocalTime now = LocalTime.now();
//        Time orderTime = Time.valueOf(now);
//        long orderTimeL = orderTime.getTime();
//        currentOrder.setOrderTime(orderTimeL);
//        currentOrder.setStatus(OrderStatus.WAITINGPAYMENT.name());
//        return this;
//    }
//
//    public Order build(){
//        return currentOrder;
//    }
//}
