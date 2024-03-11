package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userId, String uname, String fname, String lname, String email, String pass, String phone) {
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setusername(uname);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(email);
		userPayload.setPassword(pass);
		userPayload.setNumber(phone);
		
		Response response = UserEndPoints.createUser(userPayload);		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertTrue(response.statusCode() == 200);
	}
}
