package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);

        Optional<Product> op = productRepository.findById(productId);

        if(op.isPresent() ){
            String productName = op.get().getProductName();
            //Product id was found!
            //review.setProductID(productId);
            review.setProduct(op.get());
            Review r = reviewRepository.save(review);

            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", r.getReviewID().toString());
            responseHeader.set("productID", productId.toString());
            return ResponseEntity.ok().headers(responseHeader).body(r);
        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("productID", "null");
            return ResponseEntity.notFound().headers(responseHeader).build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Product> op = productRepository.findById(productId); //Is the product ID valid?
        if(op.isPresent()){
            List<Review> list = reviewRepository.findByProduct(op.get());
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("productID", productId.toString());
            return ResponseEntity.ok().headers(responseHeader).body(list);

        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("productID", "null");
            return  ResponseEntity.notFound().headers(responseHeader).build();
        }
    }
}