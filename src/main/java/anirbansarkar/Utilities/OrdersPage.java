package anirbansarkar.Utilities;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPage extends AbstractComponents {

	WebDriver driver;

	public OrdersPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//tr[@class='ng-star-inserted']/td[2]")
	List<WebElement> orders;

	public boolean verifyOrderDisplayed(String orderName) throws InterruptedException {

		Boolean match = orders.stream().anyMatch(order -> order.getText().equals(orderName));
		return match;

	}

}
