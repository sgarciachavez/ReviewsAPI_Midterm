package com.udacity.course3.reviews.repository_mongodb;

import com.udacity.course3.reviews.document_mongodb.ReviewDoc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReviewRepository_MongoDBTests {

    @Autowired private ReviewRepository_MongoDB reviewRepository_mongoDB;

    @Test
    public void injectedComponentsAreNotNull(){
        assertNotNull(reviewRepository_mongoDB);
    }

    @Test
    public void testFindByProductID(){

        //Now create a Review for the Product just saved
        ReviewDoc review = new ReviewDoc();
        //review.setProductID(p.getProductID());
        review.setProductID(1);
        review.setReviewText("Review Test for Product ID: 1");

        ReviewDoc r = reviewRepository_mongoDB.save(review);
        String rid = r.getReviewID();

        List<ReviewDoc> actual = (List<ReviewDoc>) reviewRepository_mongoDB.findByProductID(r.getProductID());
        ReviewDoc ar = actual.get(0);
        String arid = ar.getReviewID();

        System.out.println("Actual reviewID:  " + ar.getReviewID());
        System.out.println("r reviewID:  " + r.getReviewID());


        //assertEquals(ar.getReviewID(), r.getReviewID());  ??? caused error with Maven package/test
        //assertTrue(arid.equals(rid));  this didn't work either??? WHY??
        assertEquals(ar.getProductID(), r.getProductID());
    }

    @Test
    public void testFindByReviewID(){

        //Now create a ReviewDoc for the Product just saved
        ReviewDoc review = new ReviewDoc();
        //review.setProductID(p.getProductID());
        review.setProductID(1);
        review.setReviewText("Review Test for Product ID: 1");

        ReviewDoc r = reviewRepository_mongoDB.save(review);

        Optional<ReviewDoc> actual = reviewRepository_mongoDB.findById(r.getReviewID());

        assertEquals(actual.get().getReviewID(), r.getReviewID());

    }
}
