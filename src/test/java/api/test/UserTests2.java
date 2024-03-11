package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {

	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setusername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setNumber(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		logger.info("**************** Creating User ********************");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**************** Creating is Created ********************");

	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("**************** Reading User info ********************");
		Response response = UserEndPoints2.readUser(this.userPayload.getusername());
		response.then().log().all();
		
		Assert.assertTrue(response.getStatusCode() == 200);
		logger.info("**************** User info is displayed ********************");

	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("**************** Updating User info ********************");

		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPoints2.updateUser(this.userPayload.getusername(), userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getusername());		
		Assert.assertTrue(responseAfterUpdate.statusCode() == 200);
		
		logger.info("**************** User is updated ********************");

	}
	
	@Test(priority = 4)
	public void deleteUserByName() {
		logger.info("**************** Deleting User ********************");

		Response response = UserEndPoints2.deleteUser(this.userPayload.getusername());
		
		Assert.assertTrue(response.statusCode() == 200);
		logger.info("**************** User is deleted ********************");

	}
}
