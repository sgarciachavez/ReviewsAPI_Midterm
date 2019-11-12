package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired private EntityManager entityManager;
    @Autowired private ProductRepository productRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertNotNull(entityManager);
        assertNotNull(productRepository);
    }

    @Test
    public void testFindByProductID(){
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductDescription("A Description to a Test Product");

        entityManager.persist(product);

        Optional<Product> actual = productRepository.findById(product.getId());

        assertEquals(product.getId(), actual.get().getId());
    }
}
