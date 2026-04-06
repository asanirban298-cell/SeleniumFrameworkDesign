package anirbansarkar.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anirbansarkar.Utilities.AbstractComponents;

public class LandingPage extends AbstractComponents {

	WebDriver driver;

	public LandingPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement submit;

	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMsg;

	public ProductCatalogue loginApplication(String email, String pwd) {

		userEmail.sendKeys(email);
		password.sendKeys(pwd);
		submit.click();
		ProductCatalogue pc = new ProductCatalogue(driver);
		return pc;

	}

	public void goTo(String url) {

		driver.get(url);

	}

	public String getErrorMessage() {

		waitForWebElementToAppear(errorMsg);
		String errorMessage = errorMsg.getText();
		return errorMessage;

	}

}
