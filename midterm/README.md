# Reviews API 
Supports operations for writing reviews and listing reviews for a product but with no sorting or filtering. Also supports writing comments to a review. 

### Creating a Product
`POST` `/products`
```json
{
	"productName": "Avocado",
	"productDescription" : "It's a fruit"
}
```

### Retrieve a Product 
`GET` `/products/{productId}`
This feature retrieves the Product data from the database. 

### List ALL Products
`GET` `/products`

### Creating a Review
`POST` `/reviews/products/{productId}`

### Lists Reviews by product Id
`GET` `/reviews/products/{productId}`

### Creating a Comment
`POST` `/comments/reviews/{reviewId}`

### Lists Comments by Review Id
`GET` `/comments/reviews/{reviewId}`
