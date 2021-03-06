package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.steps.ProductTestSteps;
import org.fasttrackit.onlineshop.transfer.product.ProductResponse;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProductServiceIntegrationTests {

    // field-injection (injecting dependencies from IoC annotating the field itself)
    // field = instance variable
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void createProduct_whenValidRequest_thenProductisCreated() {
        productTestSteps.createProduct();

    }

    @Test
    void createProduct_whenMissingName_thenExceptionisThrown() {
        SaveProductRequest request = new SaveProductRequest();
        request.setQuantity(100);
        request.setPrice(300.5);

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type. ", e instanceof TransactionSystemException);
        }
    }
    @Test
    void getproduct_whenExistingProduct_thenReturnProduct () {
        ProductResponse product = productTestSteps.createProduct();

        ProductResponse response = productService.getProduct(product.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(product.getId()));
        assertThat(response.getId(), is(product.getName()));
        assertThat(response.getId(), is(product.getQuantity()));
        assertThat(response.getId(), is(product.getPrice()));
        assertThat(response.getId(), is(product.getDescription()));
        assertThat(response.getId(), is(product.getImageUrl()));

    }

    @Test
    void getProduct_whenNonExistingProduct_thenThrowResourceNotFoundException () {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(9999999));
    }

    @Test
    void updateProduct_whenVaidRequest_thenReturnUpdatedProduct () {
        ProductResponse product = productTestSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(product.getName() + " updated");
        request.setDescription(product.getDescription() + "updated");
        request.setPrice(product.getPrice() + 10);
        request.setQuantity(product.getQuantity() + 10);


        ProductResponse updatedProduct = productService.updateProduct(product.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(product.getId()));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getDescription(), is(request.getDescription()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));

    }

    @Test
    void deleteProduct_whenExistingProduct_thenProductDoesNotExistAnymore() {
        ProductResponse product = productTestSteps.createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));
    }

}
