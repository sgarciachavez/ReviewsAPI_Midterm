package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
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
public class CommentRepositoryTests {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertNotNull(productRepository);
        assertNotNull(reviewRepository);
        assertNotNull(commentRepository);
    }

    @Test
    public void testFindByReviewID(){
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

        //Now create a comment for the Review just saved!
        Comment comment = new Comment();
        comment.setReviewID(r.getReviewID());
        comment.setCommentText("This is a Comment for Review ID: " + r.getReviewID());

        Comment c = commentRepository.save(comment);

        List<Comment> actual = (List<Comment>) commentRepository.findByReviewID(r.getReviewID());
        Comment ac = actual.get(0);

        assertEquals(actual.size(), 1);
        assertEquals(ac.getReviewID(), r.getReviewID());
        assertEquals(ac.getCommentID(), c.getCommentID());
    }

    @Test
    public void testFindByCommenID(){
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

        //Now create a comment for the Review just saved!
        Comment comment = new Comment();
        comment.setReviewID(r.getReviewID());
        comment.setCommentText("This is a Comment for Review ID: " + r.getReviewID());

        Comment c = commentRepository.save(comment);

        Optional<Comment> actual = commentRepository.findById(c.getCommentID());

        assertEquals(actual.get().getCommentID(), c.getCommentID());
    }
}
