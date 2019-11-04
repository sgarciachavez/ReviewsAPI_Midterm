package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()){
            //The review id is valid / present -- Create the comment!
            Comment comment = new Comment();
            comment.setReviewID(reviewId);
            comment.setCommentText("Comment for reivew ID: " + reviewId);
            Comment c = commentRepository.save(comment);

            //Set Temporary date!  It's null initially until the DB does it's magic. Hibernate returns null.
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            c.setDateCreated(timestamp);

            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("commentID", c.getCommentID().toString());
            return ResponseEntity.ok().headers(responseHeader).body(c);
        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", null);
            return ResponseEntity.notFound().headers(responseHeader).build();
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()){
            List<?> list = commentRepository.findByReviewID(reviewId);
            return ResponseEntity.ok(list);
        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", null);  //this header is not being set!  Why?
            return  ResponseEntity.notFound().headers(responseHeader).build();
        }
    }
}