package id.ac.ui.cs.advprog.payment.model.Enum;

import lombok.Getter;

@Getter
public enum PaymentRequestStatus {
    WAITING_RESPONSE("WAITING_RESPONSE"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED");

    private final String status;

    private PaymentRequestStatus(String status) {
        this.status = status;
    }

    public static boolean contains(String status) {
        for (PaymentRequestStatus paymentRequestStatus : PaymentRequestStatus.values()) {
            if (paymentRequestStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}