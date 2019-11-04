CREATE TABLE Reviews(
    reviewID int NOT NULL AUTO_INCREMENT,
    productID int NOT NULL,
    reviewText varchar(100),
    dateCreated timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (reviewID),
    FOREIGN KEY (productID) REFERENCES Products(productID)
);

