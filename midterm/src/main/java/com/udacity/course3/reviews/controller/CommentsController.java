package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()){
            //The review id is valid / present -- Save the comment!
            comment.setReview(optionalReview.get());
            Comment c = commentRepository.save(comment);

            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", reviewId.toString());
            responseHeader.set("commentID", c.toString());
            return ResponseEntity.ok().headers(responseHeader).body(c);
        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", "null");
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
            List<Comment> list = commentRepository.findByReview(optionalReview.get());
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", reviewId.toString());
            return ResponseEntity.ok().headers(responseHeader).body(list);
        }else{
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", "null");  //this header is not being set!  Why?
            return  ResponseEntity.notFound().headers(responseHeader).build();
        }
    }
}