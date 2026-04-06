package anirbansarkar.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import anirbansarkar.TestComponents.BaseTest;
import anirbansarkar.pageObjects.CartPage;
import anirbansarkar.pageObjects.LandingPage;
import anirbansarkar.pageObjects.OrdersPlacementPage;
import anirbansarkar.pageObjects.PaymentsPage;
import anirbansarkar.pageObjects.ProductCatalogue;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImple extends BaseTest {

	public LandingPage lp;
	public ProductCatalogue pc;
	public CartPage cp;
	public PaymentsPage pp;
	public OrdersPlacementPage op;

	@Given("I landed on Ecommerce page")
	public void i_landed_on_Ecommerce_page() throws IOException {

		lp = launchApplication();

	}

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {

		pc = lp.loginApplication(username, password);

	}

	@When("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {

		pc.addProductToCart(productName);

	}

	@And("^Checkout (.+) and submit the order+")
	public void checkout_submit_order(String productName) {

		cp = pc.goToCart();
		// Cart Page
		Assert.assertTrue(cp.verifyCartProductDisplayed(productName));
		pp = cp.goToPayments();
		// Payments Page
		pp.enterCountry("India");
		op = pp.placeOrder();

	}

	@Then("{string} message is displayed on Confirmation page")
	public void confirMessage_is_displayed_in_confirmPage(String string) {

		String confirMsg = op.getOrderConfirMsg();
		Assert.assertTrue(confirMsg.equalsIgnoreCase(string));
		driver.close();

	}

	@Then("{string} error message is displayed in login page")
	public void errorMessage_is_displayed_in_loginPage(String string) {

		Assert.assertEquals(string, lp.getErrorMessage());
		driver.close();

	}

}
