package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Integer commentID;

    @Column(name = "reviewID")
    private Integer reviewID;

    @Column(name="commentText")
    private String commentText;

    @Column(name="dateCreated", columnDefinition = "TIMESTAMP")
    private Timestamp dateCreated;

    public Integer getCommentID() {
        return commentID;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}



