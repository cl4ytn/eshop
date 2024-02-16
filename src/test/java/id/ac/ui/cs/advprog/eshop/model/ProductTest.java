package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testEditProduct(){
        this.product.setProductId("2");
        this.product.setProductName("Cat");
        this.product.setProductQuantity(5);
        productService.editProduct(this.product);

        assertEquals("2", this.product.getProductId());
        assertEquals("Cat", this.product.getProductName());
        assertEquals(5, this.product.getProductQuantity());
    }

    @Test
    void testDeleteProductById(){
        String id = this.product.getProductId();
        productService.deleteProductById(id);
        assertNull(productService.getProductId(id));
    }
}