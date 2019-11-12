CREATE TABLE Products(
        id int NOT NULL AUTO_INCREMENT,
        productName varchar(20) NOT NULL,
        productDescription varchar(100),
        dataSource varchar(20),
        dateCreated timestamp DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id)
    );


