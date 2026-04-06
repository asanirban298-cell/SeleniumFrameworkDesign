package anirbansarkar.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import anirbansarkar.TestComponents.BaseTest;
import anirbansarkar.TestComponents.BaseTest2;
import anirbansarkar.pageObjects.CartPage;
import anirbansarkar.pageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = { "ErrorHandling" },retryAnalyzer=anirbansarkar.TestComponents.Retry.class)
	public void validateError() throws InterruptedException, IOException {

		String email = "asanirban298@gmail.com";
		String pwd = "sA2!09195";

		lp.loginApplication(email, pwd);
		Assert.assertEquals("Incorrect email or password.", lp.getErrorMessage());

	}

	@Test
	public void validateProductError() throws InterruptedException, IOException {

		String email = "asanirban298@gmail.com";
		String pwd = "sA2!091959";
		String order = "ZARA COAT 3";

		// Landing Page

		ProductCatalogue pc = lp.loginApplication(email, pwd);

		// Product Catalogue Page

		pc.addProductToCart(order);
		CartPage cp = pc.goToCart();

		// Cart Page
		AssertJUnit.assertTrue(cp.verifyCartProductDisplayed(order));

	}

}
