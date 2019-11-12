package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.course3.reviews.document_mongodb.CommentDoc;
import com.udacity.course3.reviews.document_mongodb.ReviewDoc;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository_mongodb.CommentRepository_MongoDB;
import com.udacity.course3.reviews.repository_mongodb.ReviewRepository_MongoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository_MongoDB reviewRepository_mongoDB;
    @Autowired private CommentRepository_MongoDB commentRepository_mongoDB;
    @Value("${useMongoDB}") private String useMongoDB;

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
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody String json) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);

        Optional<Product> op = productRepository.findById(productId);
        if(op.isPresent() ){
            //Product id was found!
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                //JsonNode jsonNode = objectMapper.readTree(json);
                Object returnbody;
                String reviewID;
                if(useMongoDB.equalsIgnoreCase("yes")){
                    ReviewDoc reviewDoc = objectMapper.readValue(json, ReviewDoc.class);
                    reviewDoc.setProductID(productId);
                    ReviewDoc savedReviewDoc = reviewRepository_mongoDB.save(reviewDoc);
                    reviewID = savedReviewDoc.getReviewID();
                    returnbody = savedReviewDoc;
                }else{
                    Review review = objectMapper.readValue(json,Review.class);
                    review.setProduct(op.get());
                    Review savedReview = reviewRepository.save(review);  //saving to mySQL
                    reviewID = savedReview.getReviewID().toString();
                    returnbody = savedReview;
                }

                HttpHeaders responseHeader = new HttpHeaders();
                responseHeader.set("reviewID", reviewID);
                responseHeader.set("productID", productId.toString());
                return ResponseEntity.ok().headers(responseHeader).body(returnbody);
            }catch(Exception e){
                System.out.println(e.toString());
            }
            return ResponseEntity.unprocessableEntity().build();

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

        System.out.println("Use Mongo DB??? " + useMongoDB);
        Optional<Product> op = productRepository.findById(productId); //Is the product ID valid?
        if(op.isPresent()){
            List<?> returnlist;
            if(useMongoDB.equalsIgnoreCase("yes")){
                List<ReviewDoc> list = reviewRepository_mongoDB.findByProductID(op.get().getId()); //This list excludes the comments list WHY????
                //I should NOT have to do the below,  but I couldn't get @onetomany relationship working with MongoDB!!!!
                //Work -- around!
                list.forEach((rdoc)->{
                    List<CommentDoc> clist = commentRepository_mongoDB.findByReviewID(rdoc.getReviewID());
                    rdoc.setComments(clist);
                });
                //Work -- around!
                returnlist = list;
            }else{
                //Retrieve from mysql
                List<Review> list = (List<Review>)reviewRepository.findByProduct(op.get());
                returnlist = list;
            }

            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("productID", productId.toString());
            return ResponseEntity.ok().headers(responseHeader).body(returnlist);

        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("productID", "null");
            return  ResponseEntity.notFound().headers(responseHeader).build();
        }
    }
}