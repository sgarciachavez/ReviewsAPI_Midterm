package com.udacity.course3.reviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.course3.reviews.document_mongodb.CommentDoc;
import com.udacity.course3.reviews.document_mongodb.ReviewDoc;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
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
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired private CommentRepository commentRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private CommentRepository_MongoDB commentRepository_mongoDB;
    @Autowired private ReviewRepository_MongoDB reviewRepository_mongoDB;
    @Value("${useMongoDB}") private String useMongoDB;

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
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") String reviewId, @RequestBody String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        String commentID = "";
        Object returnbody = new Object();
        try{
            if(useMongoDB.equalsIgnoreCase("yes")) {
                Optional<ReviewDoc> optionalReview = reviewRepository_mongoDB.findById(reviewId);
                if(optionalReview.isPresent()){
                    CommentDoc comment = objectMapper.readValue(json, CommentDoc.class);
                    comment.setReviewID(reviewId);
                    CommentDoc savedComment = commentRepository_mongoDB.save(comment);
                    commentID = savedComment.getId();
                    returnbody = savedComment;
                }else{
                    reviewId = null;
                }
            }else{
                Optional<Review> optionalReview = reviewRepository.findById(Integer.valueOf(reviewId));
                if(optionalReview.isPresent()){
                    Comment comment = objectMapper.readValue(json, Comment.class);
                    comment.setReview(optionalReview.get());
                    Comment savedComment = commentRepository.save(comment);
                    commentID = savedComment.getCommentID().toString();
                    returnbody = savedComment;
                }else{
                    reviewId = null;
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }

        if(reviewId != null){
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set("reviewID", reviewId.toString());
            responseHeader.set("commentID", commentID);
            return ResponseEntity.ok().headers(responseHeader).body(returnbody);
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
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") String reviewId) {
        if(useMongoDB.equalsIgnoreCase("yes")) {
            Optional<ReviewDoc> optionalReview = reviewRepository_mongoDB.findById(reviewId);
            if(optionalReview.isPresent()){
                List<CommentDoc> list = commentRepository_mongoDB.findByReviewID(reviewId);
                HttpHeaders responseHeader = new HttpHeaders();
                responseHeader.set("reviewID", reviewId.toString());
                return ResponseEntity.ok().headers(responseHeader).body(list);
            }
        }else{
            Optional<Review> optionalReview = reviewRepository.findById(Integer.valueOf(reviewId));
            if(optionalReview.isPresent()){
                List<Comment> list = commentRepository.findByReview(optionalReview.get());
                HttpHeaders responseHeader = new HttpHeaders();
                responseHeader.set("reviewID", reviewId.toString());
                return ResponseEntity.ok().headers(responseHeader).body(list);
            }
        }

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("reviewID", "null");
        return  ResponseEntity.notFound().headers(responseHeader).build();
    }
}