package com.udacity.course3.reviews.document_mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("Reviews")
public class ReviewDoc {
    @Id
    private String id;
    private String reviewText;
    private Integer productID;
    private String dataSource = "mongoDB";

    private Date dateCreated = new Date();
    private List<CommentDoc> comments = new ArrayList<>(); //NOT WORKING WHY?????

    public String getReviewID() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public List<CommentDoc> getComments() {
        return this.comments;
    }

    public void setComments(List<CommentDoc> comments) {
        this.comments = comments;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}