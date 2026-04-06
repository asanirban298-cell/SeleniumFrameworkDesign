package anirbansarkar.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	WebDriver driver;

	public CartPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@class='cartSection']/h3")
	List<WebElement> cartProducts;

	@FindBy(xpath = "//*[@class='totalRow']/button")
	WebElement checkOut;

	public boolean verifyCartProductDisplayed(String productName) {

		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equals(productName));
		return match;

	}

	public PaymentsPage goToPayments() {

		checkOut.click();
		PaymentsPage pp = new PaymentsPage(driver);
		return pp;

	}

}
