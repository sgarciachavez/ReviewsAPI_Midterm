package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTests {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;

    @Test
    public void injectedComponentsAreNotNull(){

        assertNotNull(productRepository);
        assertNotNull(reviewRepository);
    }

    @Test
    public void testFindByProductID(){
        //First create a Product and save it!
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductDescription("A Description to a Test Product");

        Product p = productRepository.save(product);

        //Now create a Review for the Product just saved
        Review review = new Review();
        review.setProductID(p.getProductID());
        review.setReviewText("Review Test for Product ID: " + p.getProductID());

        Review r = reviewRepository.save(review);

        List<Review> actual =(List<Review>) reviewRepository.findByProductID(p.getProductID());
        Review ar = actual.get(0);

        assertEquals(actual.size(), 1);
        assertEquals(ar.getReviewID(), r.getReviewID());
        assertEquals(ar.getProductID(), p.getProductID());
    }

    @Test
    public void testFindByReviewID(){
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductDescription("A Description to a Test Product");

        Product p = productRepository.save(product);

        //Now create a Review for the Product just saved
        Review review = new Review();
        review.setProductID(p.getProductID());
        review.setReviewText("Review Test for Product ID: " + p.getProductID());

        Review r = reviewRepository.save(review);

        Optional<Review> actual = reviewRepository.findById(r.getReviewID());

        assertEquals(actual.get().getReviewID(), r.getReviewID());

    }
}
