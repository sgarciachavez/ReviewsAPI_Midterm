package com.udacity.course3.reviews.repository_mongodb;

import com.udacity.course3.reviews.document_mongodb.ReviewDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository_MongoDB extends MongoRepository<ReviewDoc, String> {
    public List<ReviewDoc> findByProductID(Integer productID);
}
