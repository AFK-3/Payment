package id.ac.ui.cs.advprog.payment.service;

import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.repository.PaymentRequestRepository;
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
        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public List<PaymentRequest> findAll () {
        return paymentRequestRepository.findAll();
    }

    @Override
    public PaymentRequest findById (String id) {
        return paymentRequestRepository.findById(id).isPresent() ? paymentRequestRepository.findById(id).get() : null;
    }

    @Override
    public List<PaymentRequest> findByUsername (String buyerUsername) {
        return paymentRequestRepository.findByBuyerUsername(buyerUsername);
    }

    @Override
    public PaymentRequest update (PaymentRequest paymentRequest) {
        Optional<PaymentRequest> matchedPaymentRequestOpt = paymentRequestRepository.findById(paymentRequest.getId());
        if (! matchedPaymentRequestOpt.isPresent()) return null;
        PaymentRequest matchedPaymentRequest = matchedPaymentRequestOpt.get();
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
