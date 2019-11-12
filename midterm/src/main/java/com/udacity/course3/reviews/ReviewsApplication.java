package com.udacity.course3.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableMongoRepositories ???????
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
        /*
		try{
			String url1 = "jdbc:mysql://localhost:3306/CustomerReviewsDB";
			String username="?user=JavaDeveloper";
			String password="&password=midtermproject";
			String tz = "&serverTimezone=UTC&useLegacyDatetimeCode=false";
			String url = url1 + username + password + tz;

			try(Connection con = DriverManager.getConnection(url)){
				System.out.println("Connected to " + con.getMetaData().getDatabaseProductName());
			}
		}catch(SQLException e){
			System.out.println("SQLException  " + e.getMessage());
			System.out.println("SQLState  " + e.getSQLState());
			System.out.println("VendorError  " + e.getErrorCode());
		}
		*/

        //TEST Mongo DB
//		String uri = "mongodb://testuser:password@127.0.0.1:27017/finaldb";
//		MongoClient mongoClient = MongoClients.create(uri);
//		MongoDatabase database = mongoClient.getDatabase("finaldb");
//		database.createCollection("patients");
//		MongoCollection<Document> patients = database.getCollection("patients");
//		//create a document
//		Document patient = new Document()
//				.append("first_name", "Sylvia")
//				.append("last_name", "Garcia")
//				.append("age", 54);
//		patients.insertOne(patient);
//
//		ObjectId _id = patient.getObjectId("_id");
//		System.out.println("Object ID == " + _id);
//
//		mongoClient.close();


	}

}