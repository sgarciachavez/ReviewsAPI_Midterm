package com.udacity.course3.reviews.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Integer commentID;

    //@ManyToOne(targetEntity = Review.class)
    //@JoinColumn(name="reviewId", referencedColumnName = "reviewId")
    //private Review review;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="reviewID", referencedColumnName = "reviewID")
    @JsonBackReference
    private Review review;

    @Column(name="commentText")
    private String commentText;

    @Column(name="dateCreated", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp dateCreated;

    public Integer getCommentID() {
        return commentID;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
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



