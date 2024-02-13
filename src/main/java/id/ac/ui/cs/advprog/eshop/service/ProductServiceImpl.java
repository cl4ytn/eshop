package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductId("" + findAll().size());
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product getProductId(String id) {
        Product product = null;
        Iterator<Product> productIterator = productRepository.findAll();

        while(productIterator.hasNext()) {
            Product item = productIterator.next();

            if (item.getProductId().equals(id)) {
                product = item;
                break;
            }
        }
        return product;
    }

    @Override
    public boolean deleteProductById(String productId) {
        Product id = getProductId(productId);

        return id != null && productRepository.deleteProduct(id);
    }
}