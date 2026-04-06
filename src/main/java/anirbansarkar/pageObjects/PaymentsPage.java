package anirbansarkar.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anirbansarkar.Utilities.AbstractComponents;

public class PaymentsPage extends AbstractComponents {

	WebDriver driver;

	public PaymentsPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement selectCountry;

	@FindBy(xpath = "//a[contains(@class,'action__submit')]")
	WebElement placeOrderButton;

	By countryOptions = By.xpath("//span[@class='ng-star-inserted']");

	public void enterCountry(String countryName) {

		selectCountry.sendKeys(countryName);
		waitForElementToAppear(countryOptions);
		List<WebElement> options = driver.findElements(countryOptions);
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(countryName)) {
				option.click();
				break;
			}
		}

	}

	public OrdersPlacementPage placeOrder() {

		placeOrderButton.click();
		OrdersPlacementPage op = new OrdersPlacementPage(driver);
		return op;

	}

}
