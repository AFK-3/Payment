package id.ac.ui.cs.advprog.payment.repository;

import id.ac.ui.cs.advprog.payment.model.Builder.PaymentRequestBuilder;
import id.ac.ui.cs.advprog.payment.model.PaymentRequest;
import id.ac.ui.cs.advprog.payment.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, String> {
    List<PaymentRequest> findByBuyerUsername(String buyerUsername);
}
