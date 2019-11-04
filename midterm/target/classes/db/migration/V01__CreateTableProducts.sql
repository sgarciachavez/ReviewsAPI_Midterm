CREATE TABLE Products(
        productID int NOT NULL AUTO_INCREMENT,
        productName varchar(20) NOT NULL,
        productDescription varchar(100),
        dateCreated timestamp DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (productID)
    );


