package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;
import java.util.Map;


public interface PaymentService {
    public Payment addPayment(Order order, String method, Map<String, String> paymentData);
    public Payment setStatus(Payment payment, String status);
    public Payment getPayment(String paymentId);
    public Boolean isValidVoucher(String voucherCode);
    public Boolean hasValidData(String address, String deliveryFee);
    public Boolean validatePaymentMethod(String method, Map<String, String> paymentData);
    public List<Payment> getAllPayments();
}