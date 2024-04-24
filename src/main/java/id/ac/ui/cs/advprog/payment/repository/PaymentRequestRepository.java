package id.ac.ui.cs.advprog.payment.repository;

import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PaymentRequestRepository {
    @Autowired
    private PaymentRequestBuilder paymentRequestBuilder;
    private List<PaymentRequest> paymentRequestList = new ArrayList();

    public PaymentRequest save(PaymentRequest paymentRequest) {
        for (int index = 0; index < paymentRequestList.size(); index++) {
            PaymentRequest currentPaymentRequest = paymentRequestList.get(index);
            if (currentPaymentRequest.getId().equals(paymentRequest.getId())) {
                PaymentRequest newPaymentRequest = paymentRequestBuilder.reset()
                        .setCurrent(paymentRequest)
                        .addPaymentAmount(paymentRequest.getPaymentAmount())
                        .addBuyerUsername(paymentRequest.getBuyerUsername()).build();
                paymentRequestList.set(index, newPaymentRequest);
                return newPaymentRequest;
            }
        }
        PaymentRequest newPaymentRequest = paymentRequestBuilder.reset()
                .addPaymentAmount(paymentRequest.getPaymentAmount())
                .addBuyerUsername(paymentRequest.getBuyerUsername()).build();
        paymentRequestList.add(newPaymentRequest);
        return newPaymentRequest;
    }

    public PaymentRequest findById(UUID id) {
        for (int index = 0; index < paymentRequestList.size(); index++) {
            PaymentRequest currentPaymentRequest = paymentRequestList.get(index);
            if (currentPaymentRequest.getId().equals(id)) {
                return currentPaymentRequest;
            }
        }
        return null;
    }

    public List<PaymentRequest> findAll() {
        return paymentRequestList;
    }

    public List<PaymentRequest> findAllByBuyerUsername(String buyerUsername) {
        List<PaymentRequest> tempPaymentRequestList = new ArrayList<>();
        for (PaymentRequest paymentRequest : paymentRequestList) {
            if (paymentRequest != null && paymentRequest.getBuyerUsername().equals(buyerUsername)) {
                tempPaymentRequestList.add(paymentRequest);
            }
        }
        return tempPaymentRequestList;
    }

    public PaymentRequest deleteById(UUID id) {
        for (int index = 0; index < paymentRequestList.size(); index++) {
            PaymentRequest currentPaymentRequest = paymentRequestList.get(index);
            if (currentPaymentRequest != null && currentPaymentRequest.getId().equals(id)) {
                paymentRequestList.remove(index);
                return currentPaymentRequest;
            }
        }
        return null;
    }
}
