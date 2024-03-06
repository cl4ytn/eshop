package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234abc5678");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("1", "voucher", "WAITING_PAYMENT", paymentData);
        payments.add(payment1);

        Payment payment2 = new Payment("2", "voucher", "WAITING_PAYMENT", paymentData);
        payments.add(payment2);
    }

    @Test
    void testCreatePayment() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).createPayment(payment);

        Payment result = paymentService.addPayment(payment);
        verify(paymentRepository, times(1)).createPayment(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testCreatePaymentIfAlreadyExists() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertNull(paymentService.addPayment(payment));
        verify(paymentRepository, times(0)).createPayment(payment);
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(1);
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), payment.getStatus(),
                payment.getPaymentData());
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).createPayment(any(Payment.class));

        Payment result = paymentService.setStatus(payment.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).createPayment(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment.getId(), "MEOW"));

        verify(paymentRepository, times(0)).createPayment(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(paymentRepository).getPayment("zczc");

        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus("zczc", OrderStatus.SUCCESS.getValue()));

        verify(paymentRepository, times(0)).createPayment(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testValidVoucher() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234abc5678");
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = payment.isValidVoucher(voucherData.get("voucherCode"));
        assertTrue(validCode);
    }

    @Test
    void testInvalidVoucher() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP");
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = payment.isValidVoucher(voucherData.get("voucherCode"));
        assertFalse(validCode);
    }

    @Test
    void testEmptyVoucher() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "");
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = payment.isValidVoucher(voucherData.get("voucherCode"));
        assertFalse(validCode);
    }

    @Test
    void testValidCashData() {
        Map<String, String> cashData = Map.ofEntries(entry("address", "Jl. Test No. 1"), entry("deliveryFee", "5"))
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validData = payment.hasValidData(cashData.get("address"), cashData.get("deliveryFee"));
        assertTrue(validData);
    }

    @Test
    void testInvalidCashData() {
        Map<String, String> cashData = Map.ofEntries(entry("address", ""), entry("deliveryFee", "5"))
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validData = payment.hasValidData(cashData.get("address"), cashData.get("deliveryFee"));
        assertFalse(validData);
    }

    @Test
    void testVoucherPaymentMethod() {
        Map<String, String> voucherData = Map.ofEntries(entry("voucher", "ESHOP1234ABC5678"));
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validMethod = payment.validatePaymentMethod(payment.getMethod(), voucherData);
        assertTrue(validMethod);
    }
    @Test
    void testCashPaymentMethod() {
        Map<String, String> cashData = Map.ofEntries(entry("address", "Jl. Test No. 1"), entry("deliveryFee", "5"))
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validMethod = payment.validatePaymentMethod(payment.getMethod(), cashData);
        assertTrue(validMethod);
    }
}
