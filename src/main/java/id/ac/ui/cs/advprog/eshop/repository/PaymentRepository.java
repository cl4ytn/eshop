package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment createPayment(Payment payment) {
        if (payment.getId() == null) {
            UUID uuid = UUID.randomUUID();
            payment.setId(uuid.toString());
        }
        paymentData.add(payment);
        return payment;
    }

    public List<Payment> findAll() {
        return paymentData;
    }
    public Payment editPayment(Payment payment) {
        for (int i=0; i < paymentData.size(); i++) {
            Payment item = paymentData.get(i);
            if (item.getId().equals(payment.getId())) {
                return paymentData.set(i, payment);
            }
        }
        return null;
    }
    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)){
                return savedPayment;
            }
        }
        return null;
    }
    /*

    public boolean deleteProduct(Product product) {
        return productData.remove(product);
    }
     */
}
