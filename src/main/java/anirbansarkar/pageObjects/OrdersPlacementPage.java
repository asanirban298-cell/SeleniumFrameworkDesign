package anirbansarkar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPlacementPage {

	WebDriver driver;

	public OrdersPlacementPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@class='hero-primary']")
	WebElement orderConfirMsg;

	public String getOrderConfirMsg() {

		String confirMsg = orderConfirMsg.getText();
		return confirMsg;

	}

}
