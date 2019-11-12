package com.udacity.course3.reviews.repository_mongodb;

import com.udacity.course3.reviews.document_mongodb.CommentDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository_MongoDB extends MongoRepository<CommentDoc, String> {
    public List<CommentDoc> findByReviewID(String reviewID);
}
