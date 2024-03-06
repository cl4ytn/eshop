package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
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
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("asasdfasdf");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("asasdfasdfasdf");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        Map<String, String> voucherData = Map.ofEntries(entry("voucher", "ESHOP1234abc5678"));


        payments = new ArrayList<>();
        Payment payment1 = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);
        payments.add(payment1);

        Payment payment2 = new Payment("2", "voucher", "WAITING_PAYMENT", voucherData);
        payments.add(payment2);
    }

    @Test
    void testCreatePayment() {
        Order order = new Order("1", products, 12341234124L, "Safira");
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).createPayment(any(Payment.class));
        Map<String, String> voucherData = Map.ofEntries(entry("voucherCode", "ESHOP1234ABC5678"));

        Payment result = paymentService.addPayment(order, "voucher", voucherData);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(1);
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), OrderStatus.SUCCESS.getValue(),
                payment.getPaymentData());
        doReturn(newPayment).when(paymentRepository).editPayment(any(Payment.class));

        Payment result = paymentService.setStatus(payment, OrderStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).editPayment(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payments.get(1), "MEOW"));

        verify(paymentRepository, never()).editPayment(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(paymentRepository).findById("zczc");
        Payment payment = paymentService.getPayment("zczc");

        assertThrows(NullPointerException.class,
                () -> paymentService.setStatus(payment, OrderStatus.SUCCESS.getValue()));

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
        Map<String, String> voucherData = Map.ofEntries(entry("voucherCode", "ESHOP1234ABC5678"));

        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = paymentService.isValidVoucher(payment.getPaymentData().get("voucherCode"));
        assertTrue(validCode);
    }

    @Test
    void testInvalidVoucher() {
        Map<String, String> voucherData = Map.ofEntries(entry("voucherCode", "ESHOP"));


        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = paymentService.isValidVoucher(payment.getPaymentData().get("voucherCode"));
        assertFalse(validCode);
    }

    @Test
    void testEmptyVoucher() {
        Map<String, String> voucherData = Map.ofEntries(entry("voucherCode", ""));
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validCode = paymentService.isValidVoucher(payment.getPaymentData().get("voucherCode"));
        assertFalse(validCode);
    }

    @Test
    void testValidCashData() {
        Map<String, String> cashData = Map.ofEntries(entry("address", "Jl. Test No. 1"), entry("deliveryFee", "5"));
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validData = paymentService.hasValidData(payment.getPaymentData().get("address"), payment.getPaymentData().get("deliveryFee"));
        assertTrue(validData);
    }

    @Test
    void testInvalidCashData() {
        Map<String, String> cashData = Map.ofEntries(entry("address", ""), entry("deliveryFee", "5"));
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validData = paymentService.hasValidData(payment.getPaymentData().get("address"), payment.getPaymentData().get("deliveryFee"));
        assertFalse(validData);
    }

    @Test
    void testVoucherPaymentMethod() {
        Map<String, String> voucherData = Map.ofEntries(entry("voucherCode", "ESHOP1234ABC5678"));
        Payment payment = new Payment("1", "voucher", "WAITING_PAYMENT", voucherData);

        boolean validMethod = paymentService.validatePaymentMethod(payment.getMethod(), voucherData);
        assertTrue(validMethod);
    }
    @Test
    void testCashPaymentMethod() {
        Map<String, String> cashData = Map.ofEntries(entry("address", "Jl. Test No. 1"), entry("deliveryFee", "5"));
        Payment payment = new Payment("1", "cash", "WAITING_PAYMENT", cashData);

        boolean validMethod = paymentService.validatePaymentMethod(payment.getMethod(), payment.getPaymentData());
        assertTrue(validMethod);
    }
}
