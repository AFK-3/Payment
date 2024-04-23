package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.PaymentRequest;

import id.ac.ui.cs.advprog.payment.repository.PaymentRequestRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRequestService {
    public PaymentRequest create(PaymentRequest paymentRequest);
    public List<PaymentRequest> findAll();
    public PaymentRequest findById(UUID paymentRequestId);
    public List<PaymentRequest> findAllByBuyerId(UUID buyerId);
    public PaymentRequest update(PaymentRequest paymentRequest);
    public PaymentRequest deletePaymentRequestById(UUID paymentRequestId);
}
