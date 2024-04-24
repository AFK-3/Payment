package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentRequestImpl implements PaymentRequestService {
    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Override
    public PaymentRequest create(PaymentRequest paymentRequest) {
        return paymentRequestRepository.save(paymentRequest);
    }

    @Override
    public List<PaymentRequest> findAll() {
        return paymentRequestRepository.findAll();
    }

    @Override
    public PaymentRequest findById(UUID paymentRequestId) {
        return paymentRequestRepository.findById(paymentRequestId);
    }

    @Override
    public List<PaymentRequest> findAllByBuyerId(UUID buyerId) {
        return paymentRequestRepository.findAllByBuyerId(buyerId);
    }

    public PaymentRequest update(PaymentRequest paymentRequest) {
        return paymentRequestRepository.save(paymentRequest);
    }

    public PaymentRequest deletePaymentRequestById(UUID paymentRequestId) {
        return paymentRequestRepository.deleteById(paymentRequestId);
    }

}
