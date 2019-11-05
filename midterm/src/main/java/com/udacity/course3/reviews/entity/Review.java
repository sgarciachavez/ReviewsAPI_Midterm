package com.udacity.course3.reviews.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID")
    private Integer reviewID;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name="productId", referencedColumnName = "productId")
    private Product product;

    @Column(name="reviewText")
    private String reviewText;

    @Column(name="dateCreated", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp dateCreated;

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

}
