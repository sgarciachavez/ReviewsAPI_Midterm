package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    // TODO: Wire JPA repositories here
    @Autowired
    private ProductRepository productRepository;
    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestBody Product product) throws URISyntaxException {
        //Make sure you're getting at least a product name otherwise do not save it
        String pName = product.getProductName();
        HttpHeaders responseHeader = new HttpHeaders();

        // Do we have a NAME?????
        if(pName != null && pName.trim() != ""){

            Product p = productRepository.save(product); //Save it!
            //Set Temporary date!  It's null initially until the DB does it's magic. Hibernate returns null.
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            p.setDateCreated(timestamp);

            return findById(p.getProductID());

        }else{
            responseHeader.set("productID", null);
            return ResponseEntity.badRequest()
                    .headers(responseHeader)
                    .body("Invalid Product name");
        }

        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Product> p = productRepository.findById(id);
        HttpHeaders responseHeader = new HttpHeaders();
        if(p.isPresent()){
            responseHeader.set("productID", id.toString());
            return ResponseEntity.ok()
                    .headers(responseHeader)
                    .body(p);
        }
        else{
            responseHeader.set("productID", "null");
            return ResponseEntity.notFound()
                    .headers(responseHeader)
                    .build();
        }
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<?> listProducts() {

        return (List<Product>) productRepository.findAll();

        //throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
    }
}