package id.ac.ui.cs.advprog.eshop.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import  static  org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    @Test
    void testCreatePayment() {
        Map<String, String> paymentData = Map.ofEntries(entry("voucher", "ESHOP1234ABC5678"));
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", paymentData);

        Assertions.assertEquals("1", payment.getId());
        Assertions.assertEquals("voucher", payment.getMethod());
        Assertions.assertEquals("WAITING_PAYMENT", payment.getStatus());
        Assertions.assertEquals(paymentData, payment.getPaymentData());
    }
}
