package anirbansarkar.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import anirbansarkar.TestComponents.BaseTest;
import anirbansarkar.Utilities.Log;
import anirbansarkar.Utilities.OrdersPage;
import anirbansarkar.pageObjects.CartPage;
import anirbansarkar.pageObjects.OrdersPlacementPage;
import anirbansarkar.pageObjects.PaymentsPage;
import anirbansarkar.pageObjects.ProductCatalogue;

/**
 * SubmitOrderTest - End-to-End Test Suite for Order Placement
 * 
 * This test class validates the complete order submission workflow including: -
 * User registration with all required details - Application login with
 * registered credentials - Product selection and adding to cart - Cart
 * verification - Checkout and payment - Order confirmation - Order history
 * verification
 * 
 * Test Data: Loaded from PurchaseOrder.json data provider Groups: Purchase (for
 * test categorization)
 * 
 * @author Anirban Sarkar
 * @version 1.0
 */
public class SubmitOrderTest extends BaseTest {

	// ============== Test Configuration Constants ==============

	/** Country for order placement - Used in payments page */
	String country = "India";

	/** Expected confirmation message after successful order placement */
	String ordConfirMsg = "THANKYOU FOR THE ORDER.";

	// ============== Test Methods ==============

	/**
	 * submitOrder - Main test method for complete order placement workflow
	 * 
	 * Test Flow: 1. User Registration: Register a new user with provided
	 * credentials 2. User Login: Login with registered email and password 3.
	 * Product Selection: Add product to shopping cart 4. Cart Verification: Verify
	 * product is displayed in cart 5. Checkout: Proceed to payments page 6. Payment
	 * Details: Enter delivery country 7. Order Placement: Complete the order 8.
	 * Confirmation: Assert order confirmation message
	 * 
	 * @param input HashMap containing test data: - firstName: User's first name -
	 *              lastName: User's last name - email: User's email address -
	 *              password: User's password - mobile: User's phone number -
	 *              occupation: User's occupation
	 *              (Doctor/Student/Engineer/Scientist) - gender: User's gender
	 *              (Male/Female) - order: Product name to order (e.g., "ZARA COAT
	 *              3")
	 * 
	 * @throws InterruptedException if thread is interrupted during execution
	 * @throws IOException          if there's an issue with file operations or
	 *                              network
	 * 
	 * @testData From getData() method - PurchaseOrder.json
	 * @group Purchase
	 */
	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

		// -------- STEP 1: User Registration --------
		/*
		 * RegistrationPage rp = new RegistrationPage(driver); rp.registerUser(
		 * input.get("firstName"), // First name of the user input.get("lastName"), //
		 * Last name of the user input.get("email"), // Email for registration (also
		 * used for login) input.get("mobile"), // Mobile number
		 * input.get("occupation"), // Occupation dropdown selection
		 * input.get("gender"), // Gender radio button selection input.get("password")
		 * // Password for account );
		 */

		// -------- STEP 2: User Login --------
		// After registration, login with the same email and password
		String testCaseName1 = new Object() {
		}.getClass().getEnclosingMethod().getName();
		testCaseName = testCaseName1;
		ProductCatalogue pc = lp.loginApplication(input.get("email"), input.get("password"));
		getPicture();
		// -------- STEP 3: Product Selection --------
		// Add the specified product to the shopping cart
		Log.info("Adding product to cart...");
		pc.addProductToCart(input.get("order"));
		getPicture();
		// Navigate to cart page
		Log.info("Navigating to cart...");
		CartPage cp = pc.goToCart();
		getPicture();
		// -------- STEP 4: Cart Verification --------
		// Verify that the product was successfully added to the cart
		Assert.assertTrue(cp.verifyCartProductDisplayed(input.get("order")),
				"Product '" + input.get("order") + "' was not found in the cart");
		getPicture();
		// Proceed to checkout/payments page
		PaymentsPage pp = cp.goToPayments();
		getPicture();
		// -------- STEP 5: Payment Details --------
		// Enter the delivery country
		pp.enterCountry(country);
		getPicture();
		// Place the order
		OrdersPlacementPage op = pp.placeOrder();
		getPicture();
		// -------- STEP 6: Order Confirmation --------
		// Verify the order confirmation message
		Assert.assertTrue(op.getOrderConfirMsg().equalsIgnoreCase(ordConfirMsg),
				"Order confirmation message mismatch. Expected: " + ordConfirMsg + " but got: "
						+ op.getOrderConfirMsg());
		getPicture();
	}

	/**
	 * orderHistoryTest - Test to verify order appears in order history
	 * 
	 * This test is dependent on submitOrder test completion. It verifies that the
	 * placed order appears in the user's order history.
	 * 
	 * Test Flow: 1. Login with registered credentials 2. Navigate to orders/order
	 * history page 3. Verify the placed order is displayed in the history
	 * 
	 * @param input HashMap containing test data (email, password, and order)
	 * 
	 * @throws InterruptedException if thread is interrupted during execution
	 * @throws IOException          if there's an issue with file operations or
	 *                              network
	 * 
	 * @testData From getData() method - PurchaseOrder.json
	 * @dependsOn submitOrder test must complete successfully before this test runs
	 * @group Purchase
	 */
	@Test(dependsOnMethods = { "submitOrder" }, dataProvider = "getData", groups = { "Purchase" })
	public void orderHistoryTest(HashMap<String, String> input) throws InterruptedException, IOException {

		// -------- STEP 1: User Login --------
		String testCaseName2 = new Object() {
		}.getClass().getEnclosingMethod().getName();
		testCaseName = testCaseName2;
		ProductCatalogue pc = lp.loginApplication(input.get("email"), input.get("password"));
		getPicture();
		// -------- STEP 2: Navigate to Order History --------
		OrdersPage op = pc.goToOrders();
		getPicture();
		// -------- STEP 3: Verify Order in History --------
		// Assert that the previously placed order is visible in order history
		Assert.assertTrue(op.verifyOrderDisplayed(input.get("order")),
				"Order '" + input.get("order") + "' was not found in order history");
		getPicture();
	}

	/**
	 * getData - DataProvider method for test data parameterization
	 * 
	 * Loads test data from external JSON file (PurchaseOrder.json). This enables
	 * data-driven testing with multiple test datasets.
	 * 
	 * JSON File Location: src/test/java/anirbansarkar/data/PurchaseOrder.json
	 * 
	 * Expected JSON Structure: [ { "firstName": "John", "lastName": "Doe", "email":
	 * "john.doe@example.com", "password": "SecurePass123!", "mobile": "9876543210",
	 * "occupation": "Doctor", "gender": "Male", "order": "ZARA COAT 3" }, {
	 * "firstName": "Jane", "lastName": "Smith", "email": "jane.smith@example.com",
	 * "password": "SecurePass456!", "mobile": "9876543211", "occupation":
	 * "Engineer", "gender": "Female", "order": "ADIDAS ORIGINAL" } ]
	 * 
	 * @return Object[][] - 2D array of test data First test set: mapList.get(0)
	 *         Second test set: mapList.get(1)
	 * 
	 * @throws IOException if PurchaseOrder.json file is not found or cannot be read
	 */
	@DataProvider
	public Object[][] getData() throws IOException {

		// Load test data from JSON file
		List<HashMap<String, String>> mapList = getJSONToData(
				System.getProperty("user.dir") + "//src//test//java//anirbansarkar//data//PurchaseOrder.json");

		// Return data in TestNG expected format (2D Object array)
		// Each row represents one test execution with different data
		return new Object[][] { { mapList.get(0) }, // First dataset
				{ mapList.get(1) } // Second dataset
		};
	}

	// ============== Alternative DataProvider (Commented Out) ==============
	/*
	 * Uncomment below to use hardcoded data instead of JSON file
	 * 
	 * @DataProvider public Object[][] getData() {
	 *
	 * return new Object[][] { { "asanirban298@gmail.com", "sA2!091959",
	 * "ZARA COAT 3" }, { "asanirban298@live.com", "sA2!091959", "ADIDAS ORIGINAL" }
	 * }; }
	 */

}
