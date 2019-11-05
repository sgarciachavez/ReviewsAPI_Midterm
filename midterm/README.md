# Reviews API - Final
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

### List ALL Products
`GET` `/products`

### Creating a Review
`POST` `/reviews/products/{productId}`
```json
{
	"reviewText": "I Love Avocados! Best fruit ever!"
}
```

### Lists Reviews by product Id
`GET` `/reviews/products/{productId}`

### Creating a Comment
`POST` `/comments/reviews/{reviewId}`
```json
{
	"commentText": "Avocado on EVERYTHING!"
}
```

### Lists Comments by Review Id
`GET` `/comments/reviews/{reviewId}`
