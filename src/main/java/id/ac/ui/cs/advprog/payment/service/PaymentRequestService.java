package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.PaymentRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRequestService {
    public PaymentRequest create(PaymentRequest paymentRequest);
    public List<PaymentRequest> findAll();
    public PaymentRequest findById(String id);
    public List<PaymentRequest> findByUsername(String buyerUsername);
    public PaymentRequest update(PaymentRequest paymentRequest);
    public PaymentRequest deleteById(String id);
    public List<PaymentRequest> deleteAll();
}
