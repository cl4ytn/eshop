package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
            Payment payment = new Payment(null, method, order.getStatus(), paymentData);
            String status = validatePaymentMethod(method, paymentData) ? "SUCCESS" : "REJECTED";
            payment.setStatus(status);
            return paymentRepository.createPayment(payment);
    }
    @Override
    public Payment setStatus(Payment payment, String status) {
        if (OrderStatus.contains(status)) {
            payment.setStatus(status);
            return paymentRepository.editPayment(payment);
        } else {
            throw new IllegalArgumentException(status);
        }
    }

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Boolean isValidVoucher(String voucherCode) {
        int charCnt = 0;
        for(int i=0;i<voucherCode.length();i++)
        {
            if(Character.isDigit(voucherCode.charAt(i)))
                charCnt++;
        }
        if (charCnt != 8) {
            return false;
        }
        else if (voucherCode.length() != 16) {
            return false;
        }
        else if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }
        else {
            return true;
        }
    }
    public Boolean hasValidData(String address, String deliveryFee) {
        if (address.isEmpty() || deliveryFee.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean validatePaymentMethod(String method, Map<String, String> paymentData) {
        if (method.equals("voucher")) {
            String voucherCode = paymentData.get("voucherCode");
            return isValidVoucher(voucherCode);
        } else {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");
            return hasValidData(address, deliveryFee);
        }
    }

}