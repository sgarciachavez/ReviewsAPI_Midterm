CREATE TABLE Comments(
    commentID int NOT NULL AUTO_INCREMENT,
    reviewID int NOT NULL,
    commentText varchar(100),
    dateCreated timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (commentID),
    FOREIGN KEY (reviewID) REFERENCES Reviews(reviewID)
);

