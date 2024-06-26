package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.repository.PaymentRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class PaymentRequestImpl implements PaymentRequestService {
    @Autowired
    private PaymentRequestBuilder paymentRequestBuilder;
    @Autowired
    PaymentRequestRepository paymentRequestRepository;
    @Override
    public PaymentRequest create (PaymentRequest paymentRequest) {
        paymentRequest = paymentRequestBuilder.reset()
                .addPaymentAmount(paymentRequest.getPaymentAmount())
                .addBuyerUsername(paymentRequest.getBuyerUsername())
                .build();
        paymentRequest = paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public List<PaymentRequest> findAll () {
        return paymentRequestRepository.findAll();
    }

    @Override
    public PaymentRequest findById(String id) {
        Optional<PaymentRequest> paymentRequestOpt = paymentRequestRepository.findById(id);
        if (! paymentRequestOpt.isPresent()) return null;
        return paymentRequestOpt.get();
    }

    @Override
    public List<PaymentRequest> findByUsername (String buyerUsername) {
        return paymentRequestRepository.findByBuyerUsername(buyerUsername);
    }

    @Override
    public PaymentRequest update(PaymentRequest paymentRequest) {
        PaymentRequest matchedPaymentRequest = paymentRequestRepository.findById(paymentRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found with id: " + paymentRequest.getId()));

        PaymentRequestBuilder.updateFields(matchedPaymentRequest, paymentRequest);
        paymentRequestRepository.save(matchedPaymentRequest);
        return matchedPaymentRequest;
    }

    @Override
    public PaymentRequest deleteById (String id) {
        Optional<PaymentRequest> matchedPaymentRequestOpt = paymentRequestRepository.findById(id);
        if (! matchedPaymentRequestOpt.isPresent()) return null;
        paymentRequestRepository.deleteById(id);
        return matchedPaymentRequestOpt.get();
    }

    @Override
    @Async
    public CompletableFuture<List<PaymentRequest>> deleteAll () {
        return CompletableFuture.supplyAsync(() -> {
            List<PaymentRequest> allPaymentRequest = paymentRequestRepository.findAll();
            paymentRequestRepository.deleteAll();
            return allPaymentRequest;
        });
    }
}
