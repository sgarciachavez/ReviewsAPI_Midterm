package com.udacity.course3.reviews.repository_mongodb;

import com.udacity.course3.reviews.document_mongodb.CommentDoc;
import com.udacity.course3.reviews.document_mongodb.ReviewDoc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataMongoTest
@RunWith(SpringRunner.class)
public class CommentRepository_MongoDBTests {
    @Autowired private ReviewRepository_MongoDB reviewRepository;
    @Autowired private CommentRepository_MongoDB commentRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertNotNull(reviewRepository);
        assertNotNull(commentRepository);
    }

    @Test
    public void testFindByReviewID(){

        //Now create a Review for the Product just saved
        ReviewDoc review = new ReviewDoc();
        review.setProductID(1);
        review.setReviewText("Review Test for Product ID: 1");

        ReviewDoc r = reviewRepository.save(review);

        //Now create a comment for the Review just saved!
        CommentDoc comment = new CommentDoc();
        comment.setReviewID(r.getReviewID());
        comment.setCommentText("This is a Comment for Review ID: " + r.getReviewID());

        CommentDoc c = commentRepository.save(comment);

        List<CommentDoc> actual = (List<CommentDoc>) commentRepository.findByReviewID(r.getReviewID());
        CommentDoc ac = actual.get(0);

        assertEquals(actual.size(), 1);
        assertEquals(ac.getReviewID(), r.getReviewID());
        assertEquals(ac.getId(), c.getId());
    }

    @Test
    public void testFindByCommenID(){

        //Now create a Review for the Product just saved
        ReviewDoc review = new ReviewDoc();
        review.setProductID(1);
        review.setReviewText("Review Test for Product ID: 1");

        ReviewDoc r = reviewRepository.save(review);

        //Now create a comment for the Review just saved!
        CommentDoc comment = new CommentDoc();
        comment.setReviewID(r.getReviewID());
        comment.setCommentText("This is a Comment for Review ID: " + r.getReviewID());

        CommentDoc c = commentRepository.save(comment);

        Optional<CommentDoc> actual = commentRepository.findById(c.getId());

        assertEquals(actual.get().getId(), c.getId());
    }
}
